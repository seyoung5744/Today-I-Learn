package chap03;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class FunctionalInterfaceTest {

    public int portNum = 1234;
    public static int portNum2 = 1234555;

    public static void main(String[] args) {
        List<String> listOfStrings = List.of("test1", "", "test2");
        List<String> nonEmpty = filter(listOfStrings, (String s) -> !s.isEmpty());
        System.out.println(nonEmpty);

        System.out.println("=================\n");

        forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));

        System.out.println("=================\n");

        List<Integer> l = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
        System.out.println(l);

        Callable<Integer> c = () -> 42;
        PrivilegedAction<Integer> p = () -> 42;

        int portNumber = 1337;
        System.out.println(System.identityHashCode(portNum2));

        Runnable r1 = () -> System.out.println(portNum2 + ", " + System.identityHashCode(portNum2));
        r1.run();

        test_static();

        System.out.println("=================\n");

        // ToIntFunction<String> stringToInt = (String s) -> Integer.parseInt(s);
        ToIntFunction<String> stringToInt = Integer::parseInt;

        // BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> contains = List::contains;

        // 생성자 참조
        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();

        Function<Integer, Apple> c2 = Apple::new;
        Apple a2 = c2.apply(100);

        c2 = (weight) -> new Apple(weight);
        a2 = c2.apply(100);

        List<Integer> weights = Arrays.asList(7, 3, 4, 10);
        List<Apple> apples = map(weights, Apple::new);
        System.out.println(apples);

        BiFunction<Integer, Color, Apple> c3 = Apple::new;
        Apple a3 = c3.apply(110, Color.GREEN);
        System.out.println(a3);

        System.out.println(giveMeFruit("Orange", 110));
    }

    static Map<String, Function<Integer, Fruit>> map = new HashMap<>();

    static {
        map.put("apple", Apple::new);
        map.put("orange", Orange::new);
    }

    public static Fruit giveMeFruit(String fruit, Integer weight) {
        return map.get(fruit.toLowerCase())
            .apply(weight);
    }


    public static void test_static() {
        System.out.println(System.identityHashCode(portNum2));
    }

    @FunctionalInterface
    public interface Predicate<T> {

        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();

        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    @FunctionalInterface
    public interface Consumer<T> {

        void accept(T t);
    }

    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T t : list) {
            c.accept(t);
        }
    }

//    @FunctionalInterface
//    public interface Function<T, R> {
//        R apply(T t);
//    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
}
