package horse;

import horse.models.Account;
import horse.models.CheckingAccount;
import horse.models.SavingsAccount;

public class Main {
    public static void main(String[] args) {
        Account chekingAccount = new CheckingAccount("Personal");
        chekingAccount.deposit(500);

        Account savingsAccount = new SavingsAccount("Personal");
        savingsAccount.deposit(1000);

        try {
            chekingAccount.withdraw(200);
            savingsAccount.withdraw(1200);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }
}