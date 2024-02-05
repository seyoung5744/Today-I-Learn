package chap09._9_1;

import static chap09._9_1.Dish.menu;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import chap09._9_1.Dish.CaloricLevel;
import java.util.List;
import java.util.Map;

public class _9_1_3_MethodReference {

    public static void main(String[] args) {

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
            menu.stream()
                .collect(
                    groupingBy(dish -> {
                        if (dish.getCalories() <= 400) {
                            return CaloricLevel.DIET;
                        } else if (dish.getCalories() <= 700) {
                            return CaloricLevel.NORMAL;
                        } else {
                            return CaloricLevel.FAT;
                        }
                    }));

        System.out.println(dishesByCaloricLevel);

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel2 =
            menu.stream()
                .collect(groupingBy(Dish::getCaloricLevel));

        System.out.println(dishesByCaloricLevel2);

        int totalCalories = menu.stream().map(Dish::getCalories).reduce(0, (c1, c2) -> c1 + c2);
        System.out.println(totalCalories);

        int totalCalories2 = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories2);


    }
}
