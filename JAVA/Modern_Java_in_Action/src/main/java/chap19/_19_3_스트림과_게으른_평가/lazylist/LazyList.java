package chap19._19_3_스트림과_게으른_평가.lazylist;

import chap02.FilteringApples.Predicate;
import java.util.function.Supplier;

public class LazyList<T> implements MyList<T> {

    private final T head;
    private final Supplier<MyList<T>> tail;

    public LazyList(T head, Supplier<MyList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail.get(); // 위의 head와 달리 tail에서는 Supplier로 게으른 동작을 만들었다.
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return isEmpty() ?
            this : // 새로운 Empty<>() 를 반환할 수도 있지만 여기서는 this로 대신할 수 있다.
            p.test(head()) ?
                new LazyList<>(head(), () -> tail().filter(p)) :
                tail().filter(p);
    }

    @Override
    public String toString() {
        return String.format("LazyList[%s]", head);
    }
}
