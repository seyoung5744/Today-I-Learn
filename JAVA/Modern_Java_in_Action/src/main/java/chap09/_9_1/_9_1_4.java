package chap09._9_1;

import static chap09._9_1.Dish.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class _9_1_4 {

    public static void main(String[] args) {
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() > 300) {
                dishNames.add(dish.getName());
            }
        }

        System.out.println(dishNames);

        List<String> dishNames2 = menu.parallelStream()
            .filter(dish -> dish.getCalories() > 300)
            .map(Dish::getName)
            .collect(Collectors.toList());
        System.out.println(dishNames2);
    }
}
