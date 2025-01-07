package access;

public class BankAccountMain {

    public static void main(String[] args) {
        BackAccount account = new BackAccount();
        account.deposit(10000);
        account.withdraw(3000);
        System.out.println("balance = " + account.getBalance());
    }

}
