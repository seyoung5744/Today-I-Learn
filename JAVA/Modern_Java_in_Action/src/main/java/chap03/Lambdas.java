package chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
