package chap19._19_3_스트림과_게으른_평가.lazylist;

import chap02.FilteringApples.Predicate;

public class Empty<T> implements MyList<T> {

    @Override
    public T head() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> tail() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return this;
    }
}
