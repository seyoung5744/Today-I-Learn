package chap17._17_2;

import chap17.TempInfo;
import chap17.TempSubscriber;
import chap17.TempSubscription;
import java.util.concurrent.Flow.Publisher;

public class Main {

    public static void main(String[] args) {
        // 뉴욕에 새 Publisher 만들고 TempSubscriber 를 구독시킴
        getTemperatures("New York").subscribe(new TempSubscriber());
    }

    // 구독한 Subscriber 에게 TempSubscription 을 전송하는 Publisher 를 반환
    private static Publisher<TempInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

}
