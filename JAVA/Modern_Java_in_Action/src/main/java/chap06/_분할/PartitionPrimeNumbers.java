package chap06._분할;

import static java.util.stream.Collectors.partitioningBy;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PartitionPrimeNumbers {

    public static void main(String[] args) {
        System.out.println("Numbers partitioned in prime and non-prime: " + partitionPrimes(100));
//        System.out.println(Arrays.toString(makePrime(10)));
    }

    public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(
            partitioningBy(candidate -> isPrimeStream2(candidate))
        );
    }

    // O(n)
    public static boolean isPrimeStream1(int candidate) {
        return candidate >= 2 && IntStream.range(2, candidate)
            .noneMatch(i -> candidate % i == 0);
    }

    // O(sqrt(n))
    public static boolean isPrimeStream2(int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);

        return candidate >= 2 && IntStream.rangeClosed(2, candidateRoot)
            .noneMatch(i -> candidate % i == 0);
    }

    // O(n)
    public static boolean isPrime1(int candidate) {
        if (candidate < 2) {
            return false;
        }

        for (int i = 2; i < candidate; i++) {
            if (candidate % i == 0) {
                return false;
            }
        }

        return true;
    }

    // O(sqrt(n))
    public static boolean isPrime2(int candidate) {
        if (candidate < 2) {
            return false;
        }

        for (int i = 2; i <= (int) Math.sqrt(candidate); i++) {
            if (candidate % i == 0) {
                return false;
            }
        }

        return true;
    }

    //  에라토스테네스의 체
    private static boolean[] makePrime(int candidate) {
        boolean[] prime = new boolean[candidate + 1];

        // 소수가 아닌 index : true
        // 소수인 index : false
        if (candidate < 2) {
            return new boolean[]{true, true};
        }

        prime[0] = prime[1] = true;

        for (int i = 2; i <= (int) Math.sqrt(candidate); i++) {
            if (prime[i]) {
                continue;
            }

            for (int j = i * i; j < prime.length; j += i) {
                prime[j] = true;
            }
        }

        return prime;
    }
}
