package static2.ex;

public class MathArrayUtils {

    private MathArrayUtils() {}

    public static int sum(int[] array) {
        int result = 0;

        for (int num : array) {
            result += num;
        }

        return result;
    }

    public static double average(int[] array) {
        return (double) sum(array) / array.length;
    }

    public static int min(int[] array) {
        int min = Integer.MAX_VALUE;

        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }

        return min;
    }

    public static int max(int[] array) {
        int max = Integer.MIN_VALUE;

        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }

        return max;
    }
}
