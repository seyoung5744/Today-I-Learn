import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void multiplicationTest() {
        Dollar five = new Dollar(5);

        assertThat(new Dollar(10)).isEqualTo(five.times(2));
        assertThat(new Dollar(15)).isEqualTo(five.times(3));
    }

    @Test
    public void equality() {
        assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
        assertThat(new Dollar(5).equals(new Dollar(5))).isTrue();

        assertThat(new Dollar(5)).isNotEqualTo(new Dollar(6));
        assertThat(new Dollar(5).equals(new Dollar(6))).isFalse();
    }

    @Test
    public void francMultiplicationTest() {
        Franc five = new Franc(5);

        assertThat(new Franc(10)).isEqualTo(five.times(2));
        assertThat(new Franc(15)).isEqualTo(five.times(3));
    }
}

class Dollar {

    private int amount;

    Dollar(int amount) {
        this.amount = amount;
    }

    Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        Dollar dollar = (Dollar) obj;
        return amount == dollar.amount;
    }
}

class Franc {

    private int amount;

    Franc(int amount) {
        this.amount = amount;
    }

    Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        Franc franc = (Franc) obj;
        return amount == franc.amount;
    }
}

