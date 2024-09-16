package zerobase.dividend.model.constants;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Month {

    JAN("Jan", 1),
    FEB("Feb", 2),
    MAR("Mar", 3),
    APR("Apr", 4),
    MAY("May", 5),
    JUN("Jun", 6),
    JUL("Jul", 7),
    AUG("Aug", 8),
    SEP("Sep", 9),
    OCT("Oct", 10),
    NOV("Nov", 11),
    DEC("Dec", 12);

    private final String s;
    private final int number;

    public static int strToNumber(String s) {
        return Arrays.stream(Month.values())
            .filter(month -> month.s.equals(s))
            .findFirst()
            .map(Month::getNumber)
            .orElseThrow(() -> new RuntimeException("Unexpected Month enum value -> " + s));
    }
}
