package chap09._9_4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Peek {

    public static void main(String[] args) {
        consumingBecauseOfTerminalOperation();
        informationLogging();
    }

    private static void consumingBecauseOfTerminalOperation() {
        List<Integer> numbers = Stream.of(2, 3, 4, 5)
            .map(x -> x + 17)
            .filter(x -> x % 2 == 0)
            .limit(3)
            .collect(Collectors.toList());
        System.out.println(numbers);
    }

    private static void informationLogging() {
        List<Integer> result = Stream.of(2, 3, 4, 5)
            .peek(x -> System.out.println("taking from stream: " + x))
            .map(x -> x + 17)
            .peek(x -> System.out.println("after map: " + x))
            .filter(x -> x % 2 == 0)
            .peek(x -> System.out.println("after filter: " + x))
            .limit(3)
            .peek(x -> System.out.println("after limit: " + x))
            .collect(Collectors.toList());
        System.out.println(result);
    }
}
