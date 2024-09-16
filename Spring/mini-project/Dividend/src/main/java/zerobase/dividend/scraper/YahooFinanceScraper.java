package zerobase.dividend.scraper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.Dividend;
import zerobase.dividend.model.ScrapeResult;
import zerobase.dividend.model.constants.Month;

public class YahooFinanceScraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history/?frequency=1mo&period1=%d&period2=%d&filter=div";
    private static final long START_TIME = 86400; // 60 * 60 * 24 = 60초 * 60분 * 24시간

    public ScrapeResult scrap(final Company company, long endTime) {
        try {
            final String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, endTime);

            Connection connection = Jsoup.connect(url);
            Document document = connection.get();
            Element tbody = getElementBy(document);

            List<Dividend> dividends = tbody.children().stream()
                .map(e -> createDividendBy(e.text()))
                .collect(Collectors.toList());

            return new ScrapeResult(company, dividends);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Company scrapeCompanyByTicker(String ticker) {
        return null;
    }

    private Element getElementBy(Document document) {
        Elements parsingDivs = document.getElementsByClass("table yf-ewueuo noDl");
        Element tableElement = parsingDivs.get(0);
        return tableElement.children().get(1);
    }

    private Dividend createDividendBy(String txt) {
        String[] splits = txt.split(" ");
        int month = Month.strToNumber(splits[0]);
        int day = Integer.parseInt(splits[1].replace(",", ""));
        int year = Integer.parseInt(splits[2]);
        String dividend = splits[3];
        
        return Dividend.builder()
            .date(LocalDateTime.of(year, month, day, 0, 0))
            .dividend(dividend)
            .build();
    }
}
