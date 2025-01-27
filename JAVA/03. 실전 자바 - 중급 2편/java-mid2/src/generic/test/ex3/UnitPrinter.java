package generic.test.ex3;

import generic.test.ex3.unit.BioUnit;

public class UnitPrinter {

    public static <T extends BioUnit> void printV1(Shuttle<T> unit) {
        T out = unit.out();
        System.out.println("이름: " + out.getName() + ", HP: " + out.getHp());
    }

    public static void printV2(Shuttle<? extends BioUnit> unit) {
        BioUnit bioUnit = unit.out();
        System.out.println("이름: " + bioUnit.getName() + ", HP: " + bioUnit.getHp());
    }
}
