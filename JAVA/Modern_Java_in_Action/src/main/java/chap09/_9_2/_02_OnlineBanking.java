package chap09._9_2;

public abstract class _02_OnlineBanking {

    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);

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
