package loop.ex;

public class NestedEx2 {

    public static void main(String[] args) {
        int rows = 5;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < row + 1; col++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
