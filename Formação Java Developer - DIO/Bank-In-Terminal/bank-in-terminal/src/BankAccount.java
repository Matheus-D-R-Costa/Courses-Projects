import java.util.Scanner;

public class BankAccount {
    Scanner sc = new Scanner(System.in);
    private String agency;
    private String clientName;
    private int number;
    double funds = 237.48;
    String newFunds;

    public BankAccount() {
        validationFunds();
    }
    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double deposit() {
        System.out.println("Qual o valor que o senhor(a) deseja depositar?");
        double deposit = sc.nextDouble();
        funds += deposit;
        validationFunds();
        return funds;
    }

    public double withdraw() {
        System.out.println("Qual valor o senhor(a) deseja sacar?");
        double withdraw = sc.nextDouble();
        funds -= withdraw;
        validationFunds();
        return funds;
    }

    private void validationFunds() {
        newFunds = (funds < 0) ? "Sua conta foi bloqueada, por favor entre em contato conosco." : Double.toString(funds);
    }


    public void showData() {
        System.out.println("Olá " + clientName + ", obrigado por criar uma conta em nosso banco, sua agência é " + agency + ", conta " + number + " e seu saldo " + newFunds + " já está disponível para saque.");
    }
}