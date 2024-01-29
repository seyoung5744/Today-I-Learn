package chap06._리듀싱과_요약;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.reducing;

import chap04.Dish;

public class _03_Reducing {

    public static void main(String[] args) {
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
    }

    private static int calculateTotalCalories() {
//        return menu.stream().mapToInt(Dish::getCalories).reduce(0, (i, j) -> i + j);
//        return menu.stream().mapToInt(Dish::getCalories).reduce(0, Integer::sum);
        return menu.stream().collect(reducing(0, Dish::getCalories, (i, j)-> i + j));
    }

    private static int calculateTotalCaloriesWithMethodReference() {
        return menu.stream().collect(reducing(0, // 초깃값
            Dish::getCalories, // 변환 함수
            Integer::sum)); // 합계 함수
    }

    private static int calculateTotalCaloriesWithoutCollectors() {
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private static int calculateTotalCaloriesUsingSum() {
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }
}
