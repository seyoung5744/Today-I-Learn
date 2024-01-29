package chap06._리듀싱과_요약;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;

import chap04.Dish;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.function.BinaryOperator;

public class _02_Summarizing {

    public static void main(String[] args) {
        System.out.println("Nr. of dishes: " + howManyDishes());
        System.out.println("The most caloric dish is: " + findMostCaloricDish());
        System.out.println("The most caloric dish is: " + findMostCaloricDishUsingComparator());
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Average calories in menu: " + calculateAverageCalories());
        System.out.println("Menu statistics: " + calculateMenuStatistics());
        System.out.println("Short menu: " + getShortMenu());
        System.out.println("Short menu comma separated: " + getShortMenuCommaSeparated());
    }

    // 요리 수 계산
    private static long howManyDishes() {
//        return menu.stream().count();
        return menu.stream().collect(counting());
    }

    // 스트림값에서 최댓값과 최솟값 검색
    private static Dish findMostCaloricDish() {
//        return menu.stream().reduce((d1, d2) ->  d1.getCalories() > d2.getCalories() ? d1 : d2).get();
        return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
    }

    private static Dish findMostCaloricDishUsingComparator() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
//        return menu.stream().reduce( moreCaloricOf).get();
        return menu.stream().collect(reducing(moreCaloricOf)).get();
    }

    // 요약 연산
    private static int calculateTotalCalories() {
//        return menu.stream().mapToInt(Dish::getCalories).sum();
        return menu.stream().collect(summingInt(Dish::getCalories));
    }

    private static Double calculateAverageCalories() {
        return menu.stream().collect(averagingInt(Dish::getCalories));
    }

    // 두 개 이산의 연산을 한 번에 수행
    private static IntSummaryStatistics calculateMenuStatistics() {
        return menu.stream().collect(summarizingInt(Dish::getCalories));
    }

    // 문자열 연결
    private static String getShortMenu() {
        return menu.stream().map(Dish::getName).collect(joining());
    }

    private static String getShortMenuCommaSeparated() {
        return menu.stream().map(Dish::getName).collect(joining(", "));
    }
}
