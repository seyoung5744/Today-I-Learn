package chap05;

import static chap04.Dish.*;

import chap04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Finding {

    public static void main(String[] args) {
        if(isVegetarianFriendlyMenu()) {
            System.out.println("The menu is (somewhat) vegetarian friendly");
        }

        System.out.println(isHeavyMenu());
        System.out.println(isHeavyMenu2());

        Optional<Dish> dish = findVegetarianDish();
        dish.ifPresent(d -> System.out.println(d.getName()));

        menu.stream()
            .filter(Dish::isVegetarian)
            .findAny()
            .ifPresent(d -> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree = someNumbers.stream()
            .map(n -> n * n)
            .filter(n -> n % 3 == 0)
            .findFirst();
    }

    private static boolean isVegetarianFriendlyMenu() {
        return menu.stream().anyMatch(Dish::isVegetarian);
    }

    private static boolean isHeavyMenu() {
        return menu.stream().allMatch(dish -> dish.getCalories() < 1000);
    }

    private static boolean isHeavyMenu2() {
        return menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);
    }

    private static Optional<Dish> findVegetarianDish() {
        return menu.stream().filter(Dish::isVegetarian).findAny();
    }
}
