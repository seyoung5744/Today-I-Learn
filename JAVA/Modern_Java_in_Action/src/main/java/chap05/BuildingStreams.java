package chap05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStreams {

    private final static String FILE_PATH = "C:\\data.txt";

    public static void main(String[] args) {
        // 값으로 스트림 만들기
        // Stream.of
        Stream<String> stream = Stream.of("Modern", "Java", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty()
        Stream<String> emptyStream = Stream.empty();

        // null 이 될 수 있는 객체로 스트림 만들기
        String homeValue = System.getProperty("home");
        Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(homeValue);

        Stream<String> homeValueStream2 = Stream.ofNullable(System.getProperty("home"));
        Stream<String> values = Stream.of("Modern", "Java", "In", "Action")
            .flatMap(key -> Stream.ofNullable(System.getProperty(key)));

        // 배열로 스트림 만들기
        // Arrays.stream
        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();


        // 파일로 스트림 만들기
        long uniqueWords = 0;
        try (
            Stream<String> lines = Files.lines(Paths.get(FILE_PATH), Charset.defaultCharset())
        ) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("There are " + uniqueWords + " unique words in data.txt");

        // 함수로 무한 스트림 만들기
        // Stream.iterate
        Stream.iterate(0, n -> n + 2)
            .limit(10)
            .forEach(System.out::println);

        // iterate를 이용한 피보나치
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
            .limit(20)
            .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        Stream.iterate(new int[]{0, 1}, t-> new int[]{t[1], t[0] + t[1]})
            .limit(20)
            .map(t -> t[0])
            .forEach(t -> System.out.print(t + " "));
        System.out.println("\n========================");

        // Java 9 Predicate 지원
        IntStream.iterate(0, n -> n < 100, n -> n + 4)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n========================");

        // filter??
        // 종료 시점이 없으므로 무한으로 출력
//        IntStream.iterate(0, n -> n + 4)
//            .filter(n -> n < 100)
//            .forEach(n -> System.out.print(n + " "));

        // 쇼트 서킷
        IntStream.iterate(0, n -> n + 4)
            .takeWhile(n -> n < 100)
            .forEach(n -> System.out.print(n + " "));
        System.out.println("\n========================");


        // Stream.generate를 이용한 임의의 double 스트림
        List<Double> doubleStream = Stream.generate(Math::random)
            .limit(5)
            .collect(Collectors.toList());
        System.out.println(doubleStream);
        System.out.println("========================");

        // Stream.generate을 이용한 요소 1을 갖는 스트림
        List<Integer> ones = Stream.generate(() -> 1)
            .limit(5)
            .collect(Collectors.toList());
        System.out.println(ones);
        System.out.println("========================");

        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5).forEach(System.out::println);
        System.out.println("========================");

        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = previous;
                int nextValue = previous + current;
                previous = current;
                current = nextValue;
                return oldPrevious;
            }
        };

        IntStream.generate(fib)
            .limit(10)
            .forEach(System.out::println);
    }


}
