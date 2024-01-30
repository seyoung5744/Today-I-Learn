package chap06.collector;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.toList;

import chap04.Dish;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuTest {

    public static void main(String[] args) {
        List<Dish> dishes = menu.stream().collect(new ToListCollector<Dish>());
        System.out.println(dishes);

        List<Dish> dishes2 = menu.stream().collect(toList());
        System.out.println(dishes2);

        ArrayList<Object> dishes3 = menu.stream().collect(
            ArrayList::new, // 발행
            List::add, // 누적
            List::addAll // 합침
        );
        System.out.println(dishes3);
    }

}
