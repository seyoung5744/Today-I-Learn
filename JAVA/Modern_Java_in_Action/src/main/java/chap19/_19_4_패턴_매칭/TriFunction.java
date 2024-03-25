package chap19._19_4_패턴_매칭;

public interface TriFunction<S, T, U, R> {

    R apply(S s, T t, U u);
}
