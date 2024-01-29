package chap06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class _01_GroupingTransactions {
    public static List<Transaction> transactions = Arrays.asList(
        new Transaction(Currency.EUR, 1500.0),
        new Transaction(Currency.USD, 2300.0),
        new Transaction(Currency.GBP, 9900.0),
        new Transaction(Currency.EUR, 1100.0),
        new Transaction(Currency.JPY, 7800.0),
        new Transaction(Currency.CHF, 6700.0),
        new Transaction(Currency.EUR, 5600.0),
        new Transaction(Currency.USD, 4500.0),
        new Transaction(Currency.CHF, 3400.0),
        new Transaction(Currency.GBP, 3200.0),
        new Transaction(Currency.USD, 4600.0),
        new Transaction(Currency.JPY, 5700.0),
        new Transaction(Currency.EUR, 6800.0)
    );

    public static void main(String[] args) {
        groupImperatively();
        groupImperatively();
    }

    private static void groupImperatively() {
        Map<Currency, List<Transaction>> transactionByCurrencies = new HashMap<>();

        for(Transaction transaction : transactions) {
            Currency currency = transaction.getCurrency();

            List<Transaction> transactionsForCurrency = transactionByCurrencies.get(currency);

            if(transactionsForCurrency == null) {
                transactionsForCurrency = new ArrayList<>();
                transactionByCurrencies.put(currency, transactionsForCurrency);
            }
            transactionsForCurrency.add(transaction);
        }

        System.out.println(transactionByCurrencies);
    }

    private static void groupFunctionally() {
        Map<Currency, List<Transaction>> transactionByCurrencies = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getCurrency));

        System.out.println(transactionByCurrencies);
    }

    public static class Transaction {

        private final Currency currency;
        private final double value;

        public Transaction(Currency currency, double value) {
            this.currency = currency;
            this.value = value;
        }

        public Currency getCurrency() {
            return currency;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return currency + " " + value;
        }

    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }

}
