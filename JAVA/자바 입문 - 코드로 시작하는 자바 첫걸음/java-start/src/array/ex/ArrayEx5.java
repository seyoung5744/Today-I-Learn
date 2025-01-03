package array.ex;

import java.util.Scanner;

public class ArrayEx5 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("입력받을 숫자의 개수를 입력하세요:");
        int n = sc.nextInt();

        System.out.println(n + "개의 정수를 입력하세요:");
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += sc.nextInt();
        }

        double average = (double) sum / n;
        System.out.println("입력한 정수의 합계: " + sum);
        System.out.println("입력한 정수의 평균: " + average);
    }
}
