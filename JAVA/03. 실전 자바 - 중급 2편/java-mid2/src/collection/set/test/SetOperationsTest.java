package collection.set.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetOperationsTest {

    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>(List.of(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<>(List.of(3, 4, 5, 6, 7));

        /**
         * 합집합: [1, 2, 3, 4, 5, 6, 7]
         * 교집합: [3, 4, 5]
         * 차집합: [1, 2]
         */
        System.out.println("합집합: " + union(set1, set2));
        System.out.println("교집합: " + intersection(set1, set2));
        System.out.println("차집합: " + difference(set1, set2));
    }

    private static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> union = new HashSet<>();
        union.addAll(set1);
        union.addAll(set2);
        return union;
    }

    private static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection;
    }

    private static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        return difference;
    }
}
