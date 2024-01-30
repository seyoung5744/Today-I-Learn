package chap06.collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override // 공급자
    public Supplier<List<T>> supplier() {
//        return () -> new ArrayList<T>();
        return ArrayList::new; // 수집 연산의 시발점
    }

    @Override // 누산기
    public BiConsumer<List<T>, T> accumulator() {
//        return (list, item) -> list.add(item);
        return List::add; // 탐색한 항목을 누적하고 바로 누적자를 고친다
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity(); // 항등 함수
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> { // 두 번째 콘텐츠와 함쳐서 첫 번째 누적자를 고친다.
            list1.addAll(list2); // 변경된 첫 번째 누적자를 반환한다
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(
            IDENTITY_FINISH, CONCURRENT // 컬렌터의 플래그를 IDENTITY_FINISH, CONCURRENT 로 설정한다.
        ));
    }
}
