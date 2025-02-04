package collection.set.test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class UniqueNamesTest3 {

    public static void main(String[] args) {
        Integer[] inputArr = {30, 20, 20, 10, 10};

        Set<Integer> set = new TreeSet<>(Arrays.asList(inputArr));

        for (Integer num : set) {
            System.out.println(num);
        }
    }
}
