package chap06._분할;

import static chap06.Dish.menu;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import chap06.Dish;
import chap06.Dish.Type;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CollectorsStaticMethods {

    public static void main(String[] args) {
        list();
        set();
        collection();
        count();
        summing();
        average();
        summarizing();
        join();
        max();
        min();
        reduce();
        collectAndThen();
        group();
        partition();
    }

    /**
     * 팩토리 메서드 : toList
     * 반환 형식 : List<T>
     * 설명 : 스트림의 모든 항복을 리스트로 수집
     */
    private static void list() {
        List<Dish> dishes = menu.stream().collect(toList());
        System.out.println("toList : " + dishes);
    }

    /**
     * 팩토리 메서드 : toSet
     * 반환 형식 : Set<T>
     * 설명 : 스트림의 모든 항목을 중복이 없는 집합으로 수집
     */
    private static void set() {
        Set<Dish> dishes = menu.stream().collect(toSet());
        System.out.println("toSet : " + dishes);
    }

    /**
     * 팩토리 메서드 : toCollection
     * 반환 형식 : Collection<T>
     * 설명 : 스트림의 모든 항복을 발행자가 제공하는 컬렉션으로 수집
     */
    private static void collection() {
        ArrayList<Dish> dishes = menu.stream().collect(toCollection(ArrayList::new));
        System.out.println("toCollection : " + dishes);
    }

    /**
     * 팩토리 메서드 : counting
     * 반환 형식 : Long
     * 설명 : 스트림의 항목 수 계산
     */
    private static void count() {
        long howManyDishes = menu.stream().collect(counting());
        System.out.println("counting : " + howManyDishes);
    }

    /**
     * 팩토리 메서드 : summingInt
     * 반환 형식 : Integer
     * 설명 : 스트림의 항목으로 정수 프로퍼티값을 더함
     */
    private static void summing() {
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println("summingInt : " + totalCalories);
    }

    /**
     * 팩토리 메서드 : averagingInt
     * 반환 형식 : Double
     * 설명 : 스트림의 항목의 정수 프로퍼티의 평균값 계산
     */
    private static void average() {
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println("averagingInt : " + avgCalories);
    }

    /**
     * 팩토리 메서드 : summarizingInt
     * 반환 형식 : IntSummaryStatistics
     * 설명 : 스트림 내 항목의 최댓값, 최솟값, 합계, 평균 등의 정수 정보 통계 수집
     */
    private static void summarizing() {
        IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println("summarizingInt : " + menuStatistics);
    }

    /**
     * 팩토리 메서드 : joining
     * 반환 형식 : String
     * 설명 : 스트림의 각 항목에 toString 메서드를 호출한 결과 문자열 연결
     */
    private static void join() {
        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println("joining : " + shortMenu);
    }

    /**
     * 팩토리 메서드 : maxBy
     * 반환 형식 : Optional<T>
     * 설명 : 주어진 비교자를 이용해서 스트림의 최댓값 요소를 Optional로 감싼 값을 반환. 스트림에 요소가 없을 때는 Optional.empty() 반환
     */
    private static void max() {
        Optional<Dish> fattest = menu.stream().collect(maxBy(comparingInt(Dish::getCalories)));
//        Optional<Dish> fattest = menu.stream().collect(maxBy(comparing(Dish::getCalories)));

        fattest.ifPresent(dish -> System.out.println("maxBy : " + dish));
    }

    /**
     * 팩토리 메서드 : minBy
     * 반환 형식 : Optional<T>
     * 설명 : 주어진 비교자를 이용해서 스트림의 최솟값 요소를 Optional로 감싼 값을 반환. 스트림에 요소가 없을 때는 Optional.empty() 반환
     */
    private static void min() {
        Optional<Dish> lightest = menu.stream().collect(minBy(comparingInt(Dish::getCalories)));
        lightest.ifPresent(dish -> System.out.println("minBy : " + dish));
    }

    /**
     * 팩토리 메서드 : reducing
     * 반환 형식 : The type produced by the reduction operation
     * 설명 : 누적자를 초깃값으로 설정한 다음에 BinaryOperator로 스트림의 각 요소를 반복적으로 누적자와 합쳐 스트림을 하나의 값으로 리듀싱
     */
    private static void reduce() {
        int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
        System.out.println("reducing : " + totalCalories);
    }

    /**
     * 팩토리 메서드 : collectingAndThen
     * 반환 형식 : The type returned by the transfoming function
     * 설명 : 다른 컬렉션을 감싸고 그 결과에 변환 함수 적용
     */
    private static void collectAndThen() {
        int howManyDishes = menu.stream().collect(collectingAndThen(toList(), List::size));
        System.out.println("collectingAndThen : " + howManyDishes);
    }

    /**
     * 팩토리 메서드 : groupingBy
     * 반환 형식 : Map<K, List<T>>
     * 설명 : 하나의 프로퍼티값을 기준으로 스트림의 항목을 그룹화하며 기준 프로퍼티값을 결과 맵의 키로 사용
     */
    private static void group() {
        Map<Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println("groupingBy : " + dishesByType);
    }

    /**
     * 팩토리 메서드 : partitioningBy
     * 반환 형식 : Map<Boolean, List<T>>
     * 설명 : 프레디케이트를 스트림의 각 항목에 적용한 결과로 항목 분할
     */
    private static void partition() {
        Map<Boolean, List<Dish>> vegetarianDishes = menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitioningBy : " + vegetarianDishes);
    }
}
