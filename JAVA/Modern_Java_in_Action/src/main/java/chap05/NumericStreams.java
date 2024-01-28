package chap05;

import static chap04.Dish.menu;

import chap04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumericStreams {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        Arrays.stream(numbers.toArray())
            .forEach(System.out::println);

        // 숫자 스트림으로 매핑
        int calories = menu.stream()
            .mapToInt(Dish::getCalories)
            .sum();
        System.out.println("Number of calories:" + calories);

        // 객체 스트림으로 복원
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stream = intStream.boxed();

        // max와 OptionalInt
        OptionalInt maxCalories = menu.stream()
            .mapToInt(Dish::getCalories)
            .max();

        int max;
        if (maxCalories.isPresent()) {
            max = maxCalories.getAsInt();
        } else {
            max = 1;
        }
        System.out.println(max);

        max = maxCalories.orElse(1);
        System.out.println(max);

        // 숫자 범위
        // range vs rageClosed
        IntStream.range(1, 10)
            .forEach(System.out::print);
        System.out.println("\n====================\n");
        IntStream.rangeClosed(1, 10)
            .forEach(System.out::print);
        System.out.println("\n====================\n");

        IntStream evenNumbers = IntStream.rangeClosed(1, 100) // [1, 100] 의 범위를 나타낸다.
            .filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());

        System.out.println("\n====================\n");

        // 숫자 스트림 활용 : 피타고라스 수
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
            .flatMap(a ->
                IntStream.rangeClosed(a, 100)
                    .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                    .boxed()
                    .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
            );

        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
        System.out.println("\n====================\n");

        Stream<int[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
            .flatMap(a ->
                IntStream.rangeClosed(a, 100)
                    .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                    .filter(t -> t[2] % 1 == 0))
            .map(array -> Arrays.stream(array).mapToInt(a -> (int) a).toArray());

        pythagoreanTriples2.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
        System.out.println("\n====================\n");
    }

}
