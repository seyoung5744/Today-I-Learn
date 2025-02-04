package collection.set.test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class UniqueNamesTest2 {

    public static void main(String[] args) {
        Integer[] inputArr = {30, 20, 20, 10, 10};

        Set<Integer> set = new LinkedHashSet<>(Arrays.asList(inputArr));

        for (Integer num : set) {
            System.out.println(num);
        }
    }
}
