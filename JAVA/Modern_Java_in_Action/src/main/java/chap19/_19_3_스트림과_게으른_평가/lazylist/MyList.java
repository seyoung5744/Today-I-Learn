package chap19._19_3_스트림과_게으른_평가.lazylist;

import chap02.FilteringApples.Predicate;

public interface MyList<T> {

    T head();

    MyList<T> tail();

    default boolean isEmpty() {
        return true;
    }

    MyList<T> filter(Predicate<T> p);
}
