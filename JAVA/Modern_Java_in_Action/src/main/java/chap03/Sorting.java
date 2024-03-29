package chap03;

import static java.util.Comparator.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorting {

    public static void main(String[] args) {
        // 1
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
            new Apple(80, Color.GREEN),
            new Apple(155, Color.GREEN),
            new Apple(120, Color.RED)
        ));

        // [Apple{color=GREEN, weight=80}, Apple{color=RED, weight=120}, Apple{color=GREEN, weight=155}]
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(30, Color.RED));

        // 2
        // [Apple{color=RED, weight=30}, Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(20, Color.RED));

        // 3
        // [Apple{color=RED, weight=20}, Apple{color=RED, weight=30}, Apple{color=GREEN, weight=155}]
        inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        inventory.sort(comparing(apple -> apple.getWeight()));
        System.out.println(inventory);

        // reshuffling things a little
        inventory.set(1, new Apple(10, Color.RED));

        // 4
        // [Apple{color=RED, weight=10}, Apple{color=RED, weight=20}, Apple{color=GREEN, weight=155}]
        inventory.sort(comparing(Apple::getWeight));
        System.out.println(inventory);

        // 5
        // [Apple{color=GREEN, weight=155}, Apple{color=RED, weight=20}, Apple{color=RED, weight=10}]
        inventory.sort(comparing(Apple::getWeight).reversed());
        System.out.println(inventory);

        // reshuffling things a little
        inventory.add(new Apple(10, Color.GREEN));

        // 6
        // [Apple{color=GREEN, weight=155}, Apple{color=RED, weight=20}, Apple{color=RED, weight=10}, Apple{color=GREEN, weight=10}]
        inventory.sort(comparing(Apple::getWeight)
            .reversed()
            .thenComparing(Apple::getColor));

        System.out.println(inventory);
    }

    static class AppleComparator implements Comparator<Apple> {

        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight() - a2.getWeight();
        }

    }
}
