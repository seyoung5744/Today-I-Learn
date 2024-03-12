package chap16._16_4;

import chap16._16_4.ExchangeService.Money;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll"),
        new Shop("ShopEasy"));

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), (Runnable r) -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public List<String> findPricesSequential(String product) {
        return shops.stream()
            .map(shop -> shop.getPrice(product)) // 각 상점에서 할인 전 가격 얻기
            .map(Quote::parse) // 상점에서 반환한 문자열을 Quote 객체로 변환한다.
            .map(Discount::applyDiscount) // Discount 서비스를 이용해서 각 Quote에 할인을 적용한다.
            .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
            .map(shop -> shop.getPrice(product))
            .map(Quote::parse)
            .map(Discount::applyDiscount)
            .collect(Collectors.toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = findPricesStream(product)
            .collect(Collectors.<CompletableFuture<String>>toList());

        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }

    private Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
            .map(future -> future.thenApply(Quote::parse))
            .map(future -> future.thenCompose(quote ->
                    CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)
                )
            );
    }

    public List<String> findPricesInUSD(String product) {
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();

        for (Shop shop : shops) {
            // 아래 CompletableFuture::join와 호환되도록 futurePriceInUSD의 형식만 CompletableFuture로 바꿈.
            CompletableFuture<Double> futurePriceInUSD = CompletableFuture.supplyAsync(
                    () -> shop.getPriceDouble(product)) // 제품 가격 정보를 요청하는 첫 번째 태스크를 생성
                .thenCombine(
                    CompletableFuture.supplyAsync(
                        // USD, EUR 의 환율 정보를 요청하는 독립적인 두 번 째 태스크를 생성
                        () -> ExchangeService.getRate(Money.EUR, Money.USD)),
                    (price, rate) -> price * rate // 두 결과를 곱해서 가격과 환율 정보를 합친다.
                );

            priceFutures.add(futurePriceInUSD);
        }

        // 단점: 루프 밖에서 shop에 접근할 수 없으므로 아래 getName() 호출을 주석처리함.
        // so the getName() call below has been commented out.
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .map(price -> /*shop.getName() + */ " price is " + price)
            .collect(Collectors.toList());
    }

    public List<String> findPricesInUSDJava7(String product) {
        ExecutorService executor = Executors.newCachedThreadPool(); // 태스크를 스레드 풀에 제출할 수 있도록 ExecutorService 생성
        List<Future<Double>> priceFutures = new ArrayList<>();
        for (Shop shop : shops) {
            final Future<Double> futureRate = executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    // USD, EUR 의 환율 정보를 가져올 Future 생성
                    return ExchangeService.getRate(Money.EUR, Money.USD);
                }
            });

            final Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    // 두 번째 Future로 상점에서 요청 제품의 가격을 검색
                    double priceInEUR = shop.getPriceDouble(product);
                    return priceInEUR * futureRate.get(); // 가격을 검색할 Future를 이용해서 가격과 환율을 곱한다.
                }
            });

            priceFutures.add(futurePriceInUSD);
        }

        List<String> prices = new ArrayList<>();
        for (Future<Double> priceFuture : priceFutures) {
            try {
                prices.add(/*shop.getName() +*/ " price is " + priceFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return prices;
    }

    public List<String> findPricesInUSDAddTimeout(String product) {
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();

        for (Shop shop : shops) {
            CompletableFuture<Double> futurePriceInUSD = CompletableFuture.supplyAsync(
                    () -> shop.getPriceRandomTimes(product))
                .thenCombine(
                    CompletableFuture.supplyAsync(
                            () -> ExchangeService.getRate(Money.EUR, Money.USD))
                        // 환전 서비스가 일 초 안에 결과를 제공하지 않으면 기본 환율값을 사용
                        .completeOnTimeout(ExchangeService.DEFAULT_RATE, 1, TimeUnit.SECONDS),
                    (price, rate) -> price * rate
                )
                // 3초 뒤에 작업이 완료되지 않으면 Future가 TimeoutException을 발생시키도록 설정.
                // 자바 9에서는 비동기 타임아웃 관리 기능이 추가됨.
                .orTimeout(3, TimeUnit.SECONDS);

            priceFutures.add(futurePriceInUSD);
        }

        return priceFutures.stream()
            .map(CompletableFuture::join)
            .map(price -> /*shop.getName() + */ " price is " + price)
            .collect(Collectors.toList());
    }

    public List<String> findPricesInUSD2(String product) {
        List<CompletableFuture<String>> priceFutures = new ArrayList<>();

        for (Shop shop : shops) {
            CompletableFuture<String> futurePriceInUSD = CompletableFuture.supplyAsync(
                    () -> shop.getPriceDouble(product))
                .thenCombine(
                    CompletableFuture.supplyAsync(
                        () -> ExchangeService.getRate(Money.EUR, Money.USD)),
                    (price, rate) -> price * rate
                )
                // 루프에서 상점 이름에 접근할 수 있도록 동작을 추가함. 결과적으로 CompletableFuture<String> 인스턴스를 사용할 수 있음.
                .thenApply(price -> shop.getName() + " price is " + price); // 추가

            priceFutures.add(futurePriceInUSD);
        }

        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }

    public List<String> findPricesInUSD3(String product) {
        // 루프를 매핑 함수로 바꿈...
        Stream<CompletableFuture<String>> priceFuturesStream = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(
                    () -> shop.getPriceDouble(product))
                .thenCombine(
                    CompletableFuture.supplyAsync(
                        () -> ExchangeService.getRate(Money.EUR, Money.USD)),
                    (price, rate) -> price * rate)
                .thenApply(price -> shop.getName() + " price is " + price));
        // 하지만 합치기 전에 연산이 실행되도록 CompletableFuture를 리스트로 모음
        List<CompletableFuture<String>> priceFutures = priceFuturesStream.collect(Collectors.toList());
        return priceFutures.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }
}
