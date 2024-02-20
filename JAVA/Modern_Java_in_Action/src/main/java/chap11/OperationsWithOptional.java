package chap11;

import java.util.Optional;

public class OperationsWithOptional {

    public static void main(String[] args) {
        System.out.println(max(Optional.of(3), Optional.of(4)));
        System.out.println(max(Optional.empty(), Optional.of(4)));

        Optional<Integer> opt = Optional.of(5);
        System.out.println(
            opt.or(() -> Optional.of(4))
        );
        System.out.println(
            Optional.empty().or(() -> Optional.of(4))
        );
    }

    public static Optional<Integer> max(Optional<Integer> i, Optional<Integer> j) {
        return i.flatMap(a -> j.map(b -> Math.max(a, b)));
    }
}
