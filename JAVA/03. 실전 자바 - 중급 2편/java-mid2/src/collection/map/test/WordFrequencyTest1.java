package collection.map.test;

import java.util.HashMap;
import java.util.Map;

public class WordFrequencyTest1 {

    public static void main(String[] args) {
        String text = "orange banana apple apple banana apple";

        Map<String, Integer> result = new HashMap<>();
        for (String txt : text.split(" ")) {
            result.put(txt, result.getOrDefault(txt, 0) + 1);
        }

        System.out.println(result);
    }
}
