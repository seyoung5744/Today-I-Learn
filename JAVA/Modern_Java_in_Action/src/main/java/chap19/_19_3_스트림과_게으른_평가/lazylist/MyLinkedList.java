package chap19._19_3_스트림과_게으른_평가.lazylist;

import chap02.FilteringApples.Predicate;

public class MyLinkedList<T> implements MyList<T> {

    private final T head;
    private final MyList<T> tail;

    public MyLinkedList(T head, MyList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail;
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
                new MyLinkedList<>(head(), tail().filter(p)) :
                tail().filter(p);
    }
}
