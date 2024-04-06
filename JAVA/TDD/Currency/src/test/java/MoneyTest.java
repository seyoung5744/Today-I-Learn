import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void multiplicationTest() {
        //given
        Dollar five = new Dollar(5);

        //when
        five.times(2);

        //then
        assertThat(five.amount).isEqualTo(10);
    }

}

class Dollar {

    int amount;

    Dollar(int amount) {
        this.amount = amount;
    }

    void times(int multiplier) {
        amount *= multiplier;
    }
}
