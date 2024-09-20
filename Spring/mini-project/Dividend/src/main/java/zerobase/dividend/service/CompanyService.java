package zerobase.dividend.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zerobase.dividend.exception.impl.NoCompanyException;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.ScrapeResult;
import zerobase.dividend.persist.CompanyRepository;
import zerobase.dividend.persist.DividendRepository;
import zerobase.dividend.persist.domain.CompanyEntity;
import zerobase.dividend.persist.domain.DividendEntity;
import zerobase.dividend.scraper.Scraper;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final Trie trie;
    private final Scraper scraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker, long endTime) {
        if (companyRepository.existsByTicker(ticker)) {
            throw new RuntimeException("already exists ticker -> " + ticker);
        }
        return storeCompanyAndDividend(ticker, endTime);
    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public List<String> getCompanyNamesByKeyword(String keyword) {
        PageRequest limit = PageRequest.of(0, 10);
        return companyRepository.findByNameStartingWithIgnoreCase(keyword, limit).stream()
            .map(CompanyEntity::getName)
            .collect(Collectors.toList());
    }

    public void addAutocompleteKeyword(String keyword) {
        trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword) {
        return (List<String>) trie.prefixMap(keyword).keySet()
            .stream().collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyword(String keyword) {
        trie.remove(keyword);
    }

    private Company storeCompanyAndDividend(String ticker, long endTime) {
        // ticker 를 기준으로 회사를 스크래핑
        Company company = scraper.scrapeCompanyByTicker(ticker);

        // 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapeResult scrapeResult = scraper.scrap(company, endTime);

        // 스크래핑 결과
        CompanyEntity companyEntity = companyRepository.save(company.toEntity());
        List<DividendEntity> dividendEntities = scrapeResult.getDividends().stream()
            .map(dividend -> dividend.toEntity(companyEntity.getId()))
            .toList();

        dividendRepository.saveAll(dividendEntities);
        return company;
    }

    public String deleteCompany(String ticker) {
        var company = companyRepository.findByTicker(ticker)
            .orElseThrow(() -> new NoCompanyException());

        dividendRepository.deleteAllByCompanyId(company.getId());
        companyRepository.delete(company);

        deleteAutocompleteKeyword(company.getName());
        return company.getName();
    }
}
