package chap19._19_5;

import java.util.function.Function;

public class Combinators {

    public static void main(String[] args) {
        System.out.println(
            repeat(3, (Integer x) -> 2 * x)
                .apply(10)
        );
    }

    static <A, B, C> Function<A, C> compose(Function<B, C> g, Function<A, B> f) {
        return x -> g.apply(f.apply(x));
    }

    static <A> Function<A, A> repeat(int n, Function<A, A> f) { // n이 0이면 '아무것도 하지 않음' 을 알리는 함수를 반환
        return n == 0 ? x -> x : compose(f, repeat(n - 1, f)); // n이 0이 아니면 f를 n-1만큼 반복 실행한 다음에 f를 한 번 더 실행한다.
    }
}
