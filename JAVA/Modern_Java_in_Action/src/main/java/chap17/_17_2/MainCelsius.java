package chap17._17_2;

import chap17.TempInfo;
import chap17.TempProcessor;
import chap17.TempSubscriber;
import chap17.TempSubscription;
import java.util.concurrent.Flow.Publisher;

public class MainCelsius {

    public static void main(String[] args) {
        getCelsiusTemperatures("New York") // 뉴욕의 섭씨 온도를 전송할 Publisher 를 만듦
            .subscribe(new TempSubscriber()); // TempSubscriber 를 Publisher로 구독
    }

    private static Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            // TempProcessor 를 만들고 Subscriber와 반환된 Publisher 사이로 연결
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }
}
