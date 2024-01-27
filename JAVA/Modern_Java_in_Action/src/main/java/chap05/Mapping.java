package chap05;

import static java.util.stream.Collectors.toList;

import chap04.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Mapping {

    public static void main(String[] args) {
        // map
        List<String> dishNames = Dish.menu.stream()
            .map(Dish::getName)
            .collect(toList());
        System.out.println(dishNames);
        System.out.println("====================\n");

        List<Integer> dishNameLengths = Dish.menu.stream()
            .map(Dish::getName)
            .map(String::length)
            .collect(toList());
        System.out.println(dishNameLengths);
        System.out.println("====================\n");

        // map
        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        List<Integer> wordLengths = words.stream()
            .map(String::length)
            .collect(toList());
        System.out.println(wordLengths);
        System.out.println("====================\n");

        // flatMap

        // fail
        words = Arrays.asList("Hello", "World", "Hello");
        List<String[]> distinctWords = words.stream()
            .map(word -> word.split(""))
            .distinct()
            .collect(toList());
        distinctWords.stream().forEach(strings -> System.out.println(Arrays.toString(strings)));
        System.out.println("====================\n");

        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> streamOfWords = Arrays.stream(arrayOfWords);
        List<Stream<String>> collect = words.stream()
            .map(word -> word.split(""))// 각 단어를 개별 문자열 배열로 변환
            .map(Arrays::stream)// 각 배열을 별도의 스트림으로 생성
            .distinct()
            .collect(toList());

        List<String> uniqueCharacters = words.stream()
            .map(word -> word.split(""))
            .flatMap(Arrays::stream)
            .distinct()
            .collect(toList());

        uniqueCharacters = words.stream()
            .flatMap((String line) -> Arrays.stream(line.split("")))
            .distinct()
            .collect(toList());
        System.out.println(uniqueCharacters);
        System.out.println("====================\n");

        // flatMap Quiz 5-2
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers1.stream()
            .map(i -> i * i)
            .collect(toList());
        System.out.println(squares);
        System.out.println("====================\n");

        List<Integer> numbers2 = Arrays.asList(6, 7, 8);
        List<int[]> pairs = numbers1.stream()
            .flatMap(i -> numbers2.stream()
                .map(j -> new int[]{i, j})
            )
            .collect(toList());
        pairs.forEach( pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
        System.out.println("\n====================\n");

        List<int[]> pairs2 = numbers1.stream()
            .flatMap(i -> numbers2.stream()
                .filter(j -> (i + j) % 3 == 0)
                .map(j -> new int[]{i, j})
            )
            .collect(toList());
        pairs2.forEach(pair -> System.out.printf("(%d, %d)", pair[0], pair[1]));
    }
}
