package chap06.collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class PartitionPrimeNumbers {

    public static void main(String[] args) {
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimesWithCustomCollector(100));
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(
            partitioningBy(candidate -> isPrime(candidate))
        );
    }

    public static boolean isPrime(int candidate) {
        return IntStream.rangeClosed(2, candidate - 1)
            .limit((long) Math.sqrt(candidate) - 1)
            .noneMatch(i -> candidate % i == 0);
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return primes.stream()
            .takeWhile(i -> i <= candidateRoot)
            .noneMatch(i -> candidate % i == 0);
    }

    // 퀴즈 6-3
/*
    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for(A item : list) {
            if(!p.test(item)) { // 리스트의 현재 항목이 프레디케이트를 만족하는지 확인
                return list.subList(0, i); // 만족하지 않으면 현재 검사한 항목의 이전 항목 하위 리스트를 반환
            }
            i++;
        }
        return list; // 시트의 모든 항복이 프레디케이트를 만족하므로 리스트 자체를 반환
    }
 */

    public static class PrimeNumbersCollector implements Collector<Integer, // 스트림 요소의 형식
        Map<Boolean, List<Integer>>, // 누적자 형식
        Map<Boolean, List<Integer>>> // 수집 연산의 결과 형식
    {

        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<Boolean, List<Integer>>() {{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }};
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                acc.get(isPrime(acc.get(true), candidate)) // isPrime 결과에 따라 소수 리스트와 비소스 리스트를 만든다.
                    .add(candidate); // candidate 를 알맞은 리스트에 추가한다.
            };
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
//            throw new UnsupportedOperationException();

            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity(); // 최종 수집 과정에서 데이터 변환이 필요하지 않으므로 항등 함수를 반환.
        }

        @Override
        public Set<Characteristics> characteristics() {
            // 발견한 소수의 순서에 의미가 있으므로 컬렉터는 IDENTITY_FINISH지만 UNORDERED, CONCURRENT는 아니다.
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
        }
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithInlineCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed()
            .collect(
                () -> new HashMap<Boolean, List<Integer>>() {{ // 발행
                    put(true, new ArrayList<>());
                    put(false, new ArrayList<>());
                }},
                (acc, candidate) -> {
                    acc.get(isPrime(acc.get(true), candidate))
                        .add(candidate);
                },
                (map1, map2) -> {
                    map1.get(true).addAll(map2.get(true));
                    map1.get(false).addAll(map2.get(false));
                }
            );
    }
}
