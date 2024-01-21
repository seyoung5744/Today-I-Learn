package chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
            new Apple(80, Color.GREEN),
            new Apple(155, Color.GREEN),
            new Apple(120, Color.RED)
        );

        // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        List<Apple> greenApples = filterApplesByColor(inventory, Color.GREEN);
        System.out.println(greenApples);

        // [Apple{color='green', weight=155}]
        List<Apple> redApples = filterApplesByColor(inventory, Color.RED);
        System.out.println(redApples);

        // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        System.out.println(greenApples2);

        // [Apple{color='green', weight=155}]
        List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        System.out.println(heavyApples2);

        // []
        List<Apple> weirdApples = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
        System.out.println(weirdApples);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>(); // 반환되는 result는 List로, 처음에는 비어 있지만 점점 녹색 사과로 채워진다.

        for (Apple apple : inventory) {
            if(Color.GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>(); // 반환되는 result는 List로, 처음에는 비어 있지만 점점 녹색 사과로 채워진다.

        for (Apple apple : inventory) {
            if(apple.getColor() == color) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if(apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // 최악의 코드 => flag 사용
    public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if((flag && apple.getColor().equals(color)) ||
                (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory) {
            if(p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    enum Color {
        RED,
        GREEN
    }

    public static class Apple {

        private int weight = 0;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public Color getColor() {
            return color;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @SuppressWarnings("boxing")
        @Override
        public String toString() {
            return String.format("Apple{color='%s', weight=%d}", color, weight);
        }
    }
}
