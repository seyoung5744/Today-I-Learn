package zerobase.dividend.model;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.dividend.persist.domain.CompanyEntity;
import zerobase.dividend.persist.domain.DividendEntity;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapeResult {

    private Company company;

    private List<Dividend> dividends;

    public ScrapeResult(Company company, List<Dividend> dividends) {
        this.company = company;
        this.dividends = dividends;
    }

    public static ScrapeResult of(CompanyEntity companyEntity, List<DividendEntity> dividendEntities) {
        return new ScrapeResult(
            Company.of(companyEntity),
            dividendEntities.stream()
                .map(Dividend::of)
                .collect(Collectors.toList()));
    }
}
