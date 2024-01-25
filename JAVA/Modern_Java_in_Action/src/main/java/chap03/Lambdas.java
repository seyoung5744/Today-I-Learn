package chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class Lambdas {

    public static void main(String[] args) {
        // 간단한 예제
        Runnable r1 = () -> System.out.println("Hello World 1"); // 람다 사옹

        Runnable r2 = new Runnable() { // 익명 클래스 사용
            @Override
            public void run() {
                System.out.println("Hello World 2");
            }
        };

        process(r1);
        process(r2);
        process(() -> System.out.println("Hello World 3"));

        // 람다로 거름
        List<Apple> inventory = Arrays.asList(
            new Apple(80, Color.GREEN),
            new Apple(155, Color.GREEN),
            new Apple(120, Color.RED)
        );
        List<Apple> redApples = filter(inventory, (Apple a) -> a.getColor() == Color.RED);
        System.out.println(redApples);

        Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        inventory.sort(c);
        System.out.println(inventory);

        // Predicate 조합
        Predicate<Apple> redApple = (Apple a) -> a.getColor() == Color.RED;
        List<Apple> collect = inventory.stream()
            .filter(redApple)
            .collect(Collectors.toList());
        System.out.println(collect);

        Predicate<Apple> notRedApple = redApple.negate();
        List<Apple> collect1 = inventory.stream()
            .filter(notRedApple).collect(Collectors.toList());
        System.out.println(collect1);

        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150);
        List<Apple> collect2 = inventory.stream().filter(redAndHeavyApple)
            .collect(Collectors.toList());
        System.out.println(collect2);

        Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(apple -> apple.getWeight() > 150)
            .or(apple -> Color.GREEN.equals(apple.getColor()));
        List<Apple> collect3 = inventory.stream().filter(redAndHeavyAppleOrGreen).collect(Collectors.toList());
        System.out.println(collect3);

        // Function 조합
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(1);
        System.out.println(result);

        h = f.compose(g);
        result = h.apply(1);
        System.out.println(result);

        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> transformationPipeline =
            addHeader.andThen(Letter::checkSpelling)
                .andThen(Letter::addFooter);

        double integrate = integrate((double x) -> x + 10, 3, 7);
        System.out.println(integrate);
    }

    public static double integrate(DoubleFunction<Double> f, double a, double b) {
        return (f.apply(a) + f.apply(b)) * (b - a) / 2.0;
    }

    public static double integrate(DoubleUnaryOperator f, double a, double b) {
        return (f.applyAsDouble(a) + f.applyAsDouble(b)) * (b - a) / 2.0;
    }

    public static class Letter {

        public static String addHeader(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }

        public static String addFooter(String text) {
            return text + " Kind regards";
        }

        public static String checkSpelling(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }

    public static void process(Runnable r) {
        r.run();
    }

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    interface ApplePredicate {

        boolean test(Apple apple);
    }

}
