package chap17._17_3;

import static chap17._17_3.TempObservable.getCelsiusTemperature;
import static chap17._17_3.TempObservable.getCelsiusTemperatures;
import static chap17._17_3.TempObservable.getNegativeTemperature;

import chap17.TempInfo;
import io.reactivex.Observable;

public class MainCelsius {

    public static void main(String[] args) {
        Observable<TempInfo> celsiusTemperature = getCelsiusTemperature("New York");
        celsiusTemperature.blockingSubscribe(new TempObserver());

        System.out.println("======================");
        Observable<TempInfo> negativeTemperature = getNegativeTemperature("Seoul");
        negativeTemperature.blockingSubscribe(new TempObserver());

        System.out.println("======================");
        Observable<TempInfo> celsiusTemperatures = getCelsiusTemperatures("New York", "Chicago", "San Francisco");
        celsiusTemperatures.blockingSubscribe(new TempObserver());
    }

}
