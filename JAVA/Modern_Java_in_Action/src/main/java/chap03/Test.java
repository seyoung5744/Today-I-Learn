package chap03;

import java.util.Comparator;

public class Test {

    public static void main(String[] args) {
        Comparator<Apple> c = Comparator.comparing(Apple::getWeight).reversed();
    }

}
