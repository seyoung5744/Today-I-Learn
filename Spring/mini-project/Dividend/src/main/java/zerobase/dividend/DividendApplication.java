package zerobase.dividend;

import zerobase.dividend.model.Company;
import zerobase.dividend.scraper.YahooFinanceScraper;

//@SpringBootApplication
public class DividendApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DividendApplication.class, args);

        YahooFinanceScraper scraper = new YahooFinanceScraper();
        Company result = scraper.scrapeCompanyByTicker("MMM");
        System.out.println(result);
    }

}
