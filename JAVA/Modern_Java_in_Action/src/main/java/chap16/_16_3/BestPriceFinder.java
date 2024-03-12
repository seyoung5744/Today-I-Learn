package chap16._16_3;

import chap16._16_2.Shop;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")/*,
      new Shop("ShopEasy")*/);

    // 상점 수만큼의 스레드를 갖는 풀을 생성(스레드 수의 범위는 0과 100사이)
    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true); // 프로그램의 종료를 방해하지 않는 데몬 스레드를 사용한다.
            return t;
        }
    });

    public List<String> findPricesSequential(String product) {
        return shops.stream()
            .map(shop -> shop.getName() + " price is " + shop.getPrice(product))
            .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
            .map(shop -> shop.getName() + " price is " + shop.getPrice(product))
            .collect(Collectors.toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync( // CompletableFuture로 각각의 가격을 비동기적으로 계산
                () -> shop.getName() + " price is " + shop.getPrice(product)
            )).collect(Collectors.toList());

        List<String> prices = priceFutures.stream()
            // 모든 비동기 동작이 끝나길 기다린다.
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
        return prices;
    }

    public List<String> findPricesFutureUseExecutor(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
            .map(shop -> CompletableFuture.supplyAsync( // CompletableFuture로 각각의 가격을 비동기적으로 계산
                () -> shop.getName() + " price is " + shop.getPrice(product),
                executor
            )).collect(Collectors.toList());

        List<String> prices = priceFutures.stream()
            // 모든 비동기 동작이 끝나길 기다린다.
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
        return prices;
    }

}
