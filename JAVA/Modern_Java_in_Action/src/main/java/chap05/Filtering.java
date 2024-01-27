package chap05;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.toList;

import chap04.Dish;
import chap04.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering {

    public static void main(String[] args) {
        // 프레디케이트로 거름
        System.out.println("Filtering with a predicate");
        List<Dish> vegetarianMenu = menu.stream()
            .filter(Dish::isVegetarian)
            .collect(Collectors.toList());
        vegetarianMenu.forEach(System.out::println);

        System.out.println("====================\n");
        // 고유 요소로 거름
        System.out.println("Filtering unique elements:");
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
            .filter(i -> i % 2 == 0)
            .distinct()
            .forEach(System.out::println);

        System.out.println("====================\n");
        // 스트림 슬라이스
        // 칼로리 값을 기준으로 리스트를 오름차순 정렬!
        List<Dish> specialMenu = Arrays.asList(
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("prawns", false, 300, Type.FISH),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER));

        System.out.println("Filtered sorted menu:");
        List<Dish> filteredMenu = specialMenu.stream()
            .filter(dish -> dish.getCalories() < 320)
            .collect(toList());
        filteredMenu.forEach(System.out::println);

        System.out.println("====================\n");
        System.out.println("Sorted menu sliced with takeWhile():");
        List<Dish> slicedMenu1 = specialMenu.stream()
            .takeWhile(dish -> dish.getCalories() < 320)
            .collect(toList());
        slicedMenu1.forEach(System.out::println);

        System.out.println("====================\n");
        System.out.println("Sorted menu sliced with dropWhile():");
        List<Dish> slicedMenu2 = specialMenu.stream()
            .dropWhile(dish -> dish.getCalories() < 320)
            .collect(toList());
        slicedMenu2.forEach(System.out::println);

        // 스트림 축소
        System.out.println("====================\n");
        System.out.println("Truncating a stream:");
        List<Dish> dishesLimit3 = menu.stream()
            .filter(dish -> dish.getCalories() > 300)
            .limit(3)
            .collect(toList());
        dishesLimit3.forEach(System.out::println);

        // 요소 생략
        System.out.println("====================\n");
        System.out.println("Skipping elements:");
        List<Dish> dishedSkip2 = menu.stream()
            .filter(dish -> dish.getCalories() > 300)
            .skip(2)
            .collect(toList());
        dishedSkip2.forEach(System.out::println);

        // Quiz 5-1
        // 처음 등장하는 두 고기 요리 필터링
        System.out.println("====================\n");
        System.out.println("처음 등장하는 두 고기 요리 필터링");
        List<Dish> dishes = menu.stream()
            .filter(dish -> dish.getType() == Type.MEAT)
            .limit(2)
            .collect(toList());
        dishes.forEach(System.out::println);
    }

}
