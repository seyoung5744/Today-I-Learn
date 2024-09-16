package zerobase.dividend.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapeResult {

    private Company company;

    private List<Dividend> dividends;

    public ScrapeResult(Company company, List<Dividend> dividends) {
        this.company = company;
        this.dividends = dividends;
    }

}
