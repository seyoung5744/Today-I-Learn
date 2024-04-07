import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void multiplicationTest() {
        Dollar five = new Dollar(5);

        Dollar product = five.times(2);
        assertThat(product.amount).isEqualTo(10);

        product = five.times(3);
        assertThat(product.amount).isEqualTo(15);
    }

    @Test
    public void equality() {
        assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
        assertThat(new Dollar(5).equals(new Dollar(5))).isTrue();

        assertThat(new Dollar(5)).isNotEqualTo(new Dollar(6));
        assertThat(new Dollar(5).equals(new Dollar(6))).isFalse();
    }
}

class Dollar {

    int amount;

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
