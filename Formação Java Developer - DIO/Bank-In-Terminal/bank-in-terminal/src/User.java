import java.util.Scanner;

public class User {

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();

        Scanner sc = new Scanner(System.in);

        System.out.println("Nos diga seu nome!");
        String clientName = sc.nextLine();
        bankAccount.setClientName(clientName);

        System.out.println("Agora, digite os números da sua agência, por favor.");
        String agency = sc.nextLine();
        bankAccount.setAgency(agency);

        System.out.println("Quase lá, digite os números da sua conta para finalizarmos o cadastro.");
        int number = sc.nextInt();
        bankAccount.setNumber(number);

        bankAccount.showData();

        bankAccount.deposit();
        bankAccount.withdraw();

        bankAccount.showData();
    }
}
