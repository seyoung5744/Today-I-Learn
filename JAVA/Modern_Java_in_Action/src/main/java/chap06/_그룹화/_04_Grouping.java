package chap06._그룹화;

import static chap06.Dish.dishTags;
import static chap06.Dish.menu;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import chap06.Dish;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class _04_Grouping {

    enum CaloricLevel {DIET, NORMAL, FAT}

    ;

    public static void main(String[] args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());

        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());

        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());

        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());

        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOptionals());

        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    // 요리의 종류 또는 칼로리로 그룹화
    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
//        return menu.stream().collect(groupingBy(Dish::getType, toList()));
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
            groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }
            )
        );
    }

    // 6.3.1 그룹화된 요소 조작
    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        // return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        // 결과 : MEAT=[pork, beef], OTHER=[french fries, pizza]
        // 문제점 : Type 중 FISH 키 자체가 사라진다.

        // 문제 해결
        // FISH=[], MEAT=[pork, beef], OTHER=[french fries, pizza]
        return menu.stream().collect(
            groupingBy(Dish::getType,
                filtering(dish -> dish.getCalories() > 500, toList())));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        return menu.stream().collect(
            groupingBy(Dish::getType,
                mapping(Dish::getName, toList())
            )
        );
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        return menu.stream().collect(
            groupingBy(Dish::getType,
                flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())
            )
        );
    }

    // 6.3.2 다수준 그룹화
    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return menu.stream().collect(
            groupingBy(Dish::getType,
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })
            )
        );
    }

    // 6.3.3 서브그룹으로 데이터 수집
    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(
            groupingBy(Dish::getType, counting())
        );
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
//        return menu.stream().collect(
//            groupingBy(Dish::getType,
//                maxBy(comparingInt(Dish::getCalories))));

        return menu.stream().collect(
            groupingBy(Dish::getType,
                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
            )
        );
    }

    // 컬렉터 결과를 다른 형식에 적용하기
    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOptionals() {
        return menu.stream().collect(
            groupingBy(Dish::getType, // 분류 함수
                collectingAndThen(
                    maxBy(Comparator.comparingInt(Dish::getCalories)), // 감싸인 컬렉텨
                    Optional::get // 변환 함수
                )
            )
        );
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return menu.stream().collect(
            groupingBy(Dish::getType,
                summingInt(Dish::getCalories)
            )
        );
    }

    // 각 요리 형식에 존재하는 모든 CaloricLevel 추출
    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
            groupingBy(Dish::getType,
                mapping(dish -> {
                        if (dish.getCalories() <= 400) {
                            return CaloricLevel.DIET;
                        } else if (dish.getCalories() <= 700) {
                            return CaloricLevel.NORMAL;
                        } else {
                            return CaloricLevel.FAT;
                        }
                    },
//                    toSet()
                    toCollection(HashSet::new)
                ))
        );
    }
}
