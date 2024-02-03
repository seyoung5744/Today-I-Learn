package chap08;


public class Transaction {

    private final Currency currency;
    private final double value;
    private final String referenceCode;

    public Transaction(Currency currency, double value, String referenceCode) {
        this.currency = currency;
        this.value = value;
        this.referenceCode = referenceCode;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    @Override
    public String toString() {
        return currency + " " + value + " " + referenceCode;
    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }


}
