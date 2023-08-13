package array;

import java.util.Arrays;
import java.util.Random;

public class RandomArray {

    private static Random random = new Random();

    public static String[] makeRandomStrings(int len, int arraySize) {
        String[] strs = new String[arraySize];

        for (int i = 0; i < arraySize; i++) {
            strs[i] = makeWord(len);
        }

        return strs;
    }

    private static String makeWord(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }

        return sb.toString();
    }
}
