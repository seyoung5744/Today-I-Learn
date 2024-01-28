package chap05;

import chap04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Reducing {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        System.out.println(" 요소의 합");
        // 요소의 합
        // for-each
        int sum = 0;
        for (int x : numbers) {
            sum += x;
        }
        System.out.println(sum);

        // reduce
        int sum2 = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum2);

        int sum3 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum3);

        int sum4 = numbers.parallelStream().reduce(0, Integer::sum);
        System.out.println(sum4);
        System.out.println("=======================\n");

        int product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println(product);

        // 초기값 없음
        Optional<Integer> optionalSum = numbers.stream().reduce((a, b) -> a + b);
        System.out.println("=======================\n");

        // 최댓값과 최솟값
        System.out.println("최댓값과 최솟값");
        int max = numbers.stream().reduce(0, Integer::max);
        System.out.println(max);

        int max2 = numbers.stream().reduce(0, (a, b) -> a > b ? a : b);
        System.out.println(max2);
        Optional<Integer> optionalMax = numbers.stream().reduce(Integer::max);

        int min = numbers.stream().reduce(Integer.MAX_VALUE, Integer::min);
        System.out.println(min);

        int min2 = numbers.stream().reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
        System.out.println(min2);
        Optional<Integer> optionalMIn = numbers.stream().reduce(Integer::min);

        System.out.println("=======================\n");

        // Quiz
        System.out.println("map 과 reduce 를 이용해서 요리 개수 계산");
        int count = Dish.menu.stream()
            .map(dish -> 1)
            .reduce(0, Integer::sum);
        System.out.println(count);

        long count2 = Dish.menu.stream().count();
        System.out.println(count2);
    }

}
