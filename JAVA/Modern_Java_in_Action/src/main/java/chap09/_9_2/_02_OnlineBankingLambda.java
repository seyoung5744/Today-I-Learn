package chap09._9_2;

import chap03.FunctionalInterfaceTest.Consumer;

public class _02_OnlineBankingLambda {

    public static void main(String[] args) {
        new _02_OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!!"));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // 더미 Customer 클래스
    static private class Customer {

    }

    // 더미 Database 클래스
    static private class Database {

        static Customer getCustomerWithId(int id) {
            return new Customer();
        }

    }
}
