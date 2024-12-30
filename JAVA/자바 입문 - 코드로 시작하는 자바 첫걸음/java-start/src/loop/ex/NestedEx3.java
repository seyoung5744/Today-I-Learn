package loop.ex;

public class NestedEx3 {

    public static void main(String[] args) {
        int rows = 5;
        for (int row = 1; row <= rows; row++) {
            for (int i = rows - row; i > 0; i--) {
                System.out.print(" ");
            }
            for (int col = 0; col < 2 * row - 1; col++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
