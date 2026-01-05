package kuke.board.articleread.learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LongToDoubleTest {

    @Test
    @DisplayName("")
    void longToDoubleTest() {
        // long 은 64bit 로 정수
        // double 은 64bit 부동소수점
        // 하지만 이때, long 은 정수만 표현하므로 더 넓은 범위의 표현이 가능.
        // 따라서 표현 범위로 인해 데이터 double 형에서 유실 발생
        long longValue = 111_111_111_111_111_111L;
        System.out.println("longValue = " + longValue);

        double doubleValue = longValue;
        System.out.println("doubleValue = " + new BigDecimal(doubleValue).toString());

        long longValue2 = (long) doubleValue;
        System.out.println("longValue2 = " + longValue2);
    }
}
