public class TPC06 {

    public static void main(String[] args) {
        // 5. 메서드 -> 동작(method), 기능(function)
        int a = 67;
        int b = 98;
        // a + b = ?
        int result = sum(a, b);
        System.out.println(result);

        int[] arr = makeArr();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        System.out.println(sum);
    }

    // 정수 2개를 매개변수로 받아서 총합을 구하여 리턴하는 메서드를 정의하시오.
    public static int sum(int a, int b) {
        return a + b;
    }

    public static int[] makeArr() {
        return new int[] {10, 20, 30};
    }
}
