import java.util.Arrays;

public class TPC07 {

    public static void main(String[] args) {
        int a = 20;
        float b = 56.7f;

        // a + b = ?
        float result1 = sum(a, b); // Call By Value
        System.out.println(result1);

        int[] arr = {1,2,3,4,5};
        int result2 = arrSum(arr); // Call By Reference
        System.out.println(result2);
    }

    private static int arrSum(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    public static float sum(int a, float b) {
        float result = a + b;
        return result;
    }
}
