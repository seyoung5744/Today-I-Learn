package chap17._17_3;

import static chap17._17_3.TempObservable.getTemperature;

import chap17.TempInfo;
import io.reactivex.Observable;

public class Main {

    public static void main(String[] args) {
        // 매 초마다 뉴욕의 온도 보고를 방출하는 Observable 만들기
        Observable<TempInfo> observable = getTemperature("New York");

        // 단순 Observer로 이 Observable 에 가입해서 온도 출력하기
        observable.blockingSubscribe(new TempObserver());

//        observable.subscribe(new TempObserver());
//
//        try {
//            Thread.sleep(10000L);
//        }
//        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

}
