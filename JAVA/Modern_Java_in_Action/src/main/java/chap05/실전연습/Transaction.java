package chap05.실전연습;

import java.util.Objects;

public class Transaction {

    private Trader trader;
    private int year;
    private int value;

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return getYear() == that.getYear() && getValue() == that.getValue() && Objects.equals(getTrader(),
            that.getTrader());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrader(), getYear(), getValue());
    }

    @Override
    public String toString() {
        return String.format("{%s, year: %d, value:%d}", trader, year, value);
    }
}
