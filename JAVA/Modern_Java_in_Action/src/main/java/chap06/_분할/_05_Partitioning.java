package chap06._분할;

import static chap06.Dish.menu;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;

import chap06.Dish;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class _05_Partitioning {

    public static void main(String... args) {
        System.out.println("Dishes partitioned by vegetarian: " + partitionByVegetarian());
        System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
        System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());

        // 퀴즈 6-2
        System.out.println(
            menu.stream().collect(partitioningBy(Dish::isVegetarian,
                partitioningBy(d -> d.getCalories() > 500)))
        );

//        System.out.println(
//            menu.stream().collect(partitioningBy(Dish::isVegetarian,
//                partitioningBy(Dish::getType) //
//            ))
//        );

        System.out.println(
            menu.stream().collect(partitioningBy(Dish::isVegetarian,
                counting()
            ))
        );
    }

    private static Map<Boolean, List<Dish>> partitionByVegetarian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian));
    }

    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
//        return menu.stream().collect(
//            groupingBy(Dish::isVegetarian,
//                groupingBy(Dish::getType))
//        );
        return menu.stream().collect(
            partitioningBy(Dish::isVegetarian,
                groupingBy(Dish::getType))
        );
    }

    private static Map<Boolean, Dish> mostCaloricPartitionedByVegetarian() {
        return menu.stream().collect(
            partitioningBy(Dish::isVegetarian,
                collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
                    Optional::get
                )
            )
        );
    }

}
