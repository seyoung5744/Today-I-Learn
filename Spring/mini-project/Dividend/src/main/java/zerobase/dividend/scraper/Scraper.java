package zerobase.dividend.scraper;

import zerobase.dividend.model.Company;
import zerobase.dividend.model.ScrapeResult;

public interface Scraper {

    public ScrapeResult scrap(final Company company, long endTime);

    public Company scrapeCompanyByTicker(String ticker);
}
