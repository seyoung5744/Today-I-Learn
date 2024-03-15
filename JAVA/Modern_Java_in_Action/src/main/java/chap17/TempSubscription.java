package chap17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Future;

public class TempSubscription implements Subscription {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
//    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    private final Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    @Override
    public void request(long n) {
        executor.submit(() -> { // 다른 스레드에서 다음 요소를 구독자에게 보낸다.
            System.out.println(Thread.currentThread().getName());
            for (long i = 0; i < n; i++) { // Subscriber가 만든 요청을 한 개씩 반복
                try {
                    subscriber.onNext(TempInfo.fetch(town)); // 현재 온도를 Subscriber 로 전달
                } catch (Exception e) {
                    subscriber.onError(e); // 온도 가져오기를 실패하면 Subscriber 로 에러를 전달
                    break;
                }
            }
        });
    }

    @Override
    public void cancel() {
        subscriber.onComplete(); // 구독이 취소되면 완료(onComplete) 신호를 Subscriber 로 전달
    }
}
