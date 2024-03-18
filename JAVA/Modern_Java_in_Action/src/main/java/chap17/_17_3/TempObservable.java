package chap17._17_3;

import chap17.TempInfo;
import io.reactivex.Observable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TempObservable {

    // emitter (명사) 방사체, (법령 등의) 발포자, (지폐 등의) 발행인
    public static Observable<TempInfo> getTemperature(String town) {
        return Observable.create(emitter -> // Observer 를 소비하는 함수로부터 Observable 만들기
            Observable.interval(1, TimeUnit.SECONDS) // 매 초마다 무한으로 증가하는 일련의 long 값을 방출하는 Observable
                .subscribe(i -> {
                    if (!emitter.isDisposed()) { // 소비된 옵저버가 아직 폐기되지 않았으면 어떤 작업을 수행(이전 에러)
                        if (i >= 5) { // 온도를 다섯 번 보고했으면 옵저버를 완료하고 스트림을 종료
                            emitter.onComplete();
                        } else {
                            try {
                                emitter.onNext(TempInfo.fetch(town)); // 아니면 온도를 Observer로 보고
                            } catch (Exception e) {
                                emitter.onError(e); // 에러가 발생하면 Observer에 알림
                            }
                        }
                    }
                })
        );
    }

    public static Observable<TempInfo> getCelsiusTemperature(String town) {
        return getTemperature(town)
            .map(temp -> new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9));
    }

    public static Observable<TempInfo> getNegativeTemperature(String town) {
        return getTemperature(town)
            .map(temp -> new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9))
            .filter(temp -> temp.getTemp() < 0);
    }

    public static Observable<TempInfo> getCelsiusTemperatures(String... towns) {
        return Observable.merge(Arrays.stream(towns)
            .map(TempObservable::getCelsiusTemperature)
            .collect(Collectors.toList())
        );
    }
}
