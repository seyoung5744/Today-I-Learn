package chap15._15_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

public class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {

    private int value = 0;
    private String name;
    private List<Subscriber<? super Integer>> subscribers = new ArrayList<>();

    public static void main(String[] args) {
        SimpleCell c3 = new SimpleCell("c3");
        SimpleCell c2 = new SimpleCell("c2");
        SimpleCell c1 = new SimpleCell("c1");

        c1.subscribe(c3);

        c1.onNext(10); // c1의 값을 10으로 갱신
        c2.onNext(20); // c2의 값을 20으로 갱신
    }

    public SimpleCell(String name) {
        this.name = name;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscribers.add(subscriber);
    }

    public void subscribe(Consumer<? super Integer> onNext) {
        subscribers.add(new Subscriber<>() {

            @Override
            public void onComplete() {}

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onNext(Integer val) {
                onNext.accept(val);
            }

            @Override
            public void onSubscribe(Subscription s) {}

        });
    }

    // 새로운 값이 있음을 모든 구독자에게 알리는 메서드
    private void notifyAllSubscribers() {
        subscribers.forEach(subscriber -> subscriber.onNext(this.value));
    }

    @Override
    public void onNext(Integer newValue) {
        // 구독한 셀에 새 값이 생겼을 떄 값을 갱신해서 반응함
        this.value = newValue;
        // 값을 콘솔에 출력하지만 실제로는 UI의 셀을 갱신할 수 있을
        System.out.println(this.name + ":" + this.value);
        // 값이 갱신되었음을 모든 구독자에게 알림
        notifyAllSubscribers();
    }

    @Override
    public void onComplete() {}

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onSubscribe(Subscription s) {}
}
