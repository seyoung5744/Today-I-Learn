package chap16._16_2;

import static chap16.Util.delay;
import static chap16.Util.format;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsyncShop {

    private final String name;
    private final Random random;

    public AsyncShop(String name) {
        this.name = name;
        this.random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        delay();
        if(true) {
            throw new RuntimeException("product not available");
        }
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

    public Future<Double> getPriceAsync(String product) {
        /*
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                // 계산이 정상적으로 종료되면 Future에 가격 정보를 저장한 채로 Future를 종료한다.
                futurePrice.complete(price);
            } catch (Exception ex) {
                // 도중에 문제가 발생하면 발생한 에러를 포함시켜 Future를 종료한다.
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        return futurePrice;
         */
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public String getName() {
        return name;
    }
}
