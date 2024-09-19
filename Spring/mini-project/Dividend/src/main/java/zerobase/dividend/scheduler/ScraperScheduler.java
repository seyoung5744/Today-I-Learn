package zerobase.dividend.scheduler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.ScrapeResult;
import zerobase.dividend.model.constants.CacheKey;
import zerobase.dividend.persist.CompanyRepository;
import zerobase.dividend.persist.DividendRepository;
import zerobase.dividend.persist.domain.CompanyEntity;
import zerobase.dividend.scraper.Scraper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    private final Scraper scraper;

    // 일정 주기마다 수행
    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");

        // 저장된 회사 목록을 조회
        List<CompanyEntity> companies = companyRepository.findAll();

        // 회사마다 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scraping scheduler is started -> " + company.getName());
            ScrapeResult scrapeResult = scraper.scrap(Company.of(company), System.currentTimeMillis() / 1000);

            // 스크래핑한 배당금 정보 중 DB에 없는 값은 저장
            scrapeResult.getDividends().stream()
                .map(dividend -> dividend.toEntity(company.getId()))
                .forEach(dividendEntity -> {
                    boolean exists = dividendRepository.existsByCompanyIdAndDate(dividendEntity.getCompanyId(), dividendEntity.getDate());
                    if (!exists) {
                        dividendRepository.save(dividendEntity);
                    }
                });

            // 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            sleep(3000);
        }
    }

    private void sleep(final int millis) {
        try {
            Thread.sleep(millis); // 3 second
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
