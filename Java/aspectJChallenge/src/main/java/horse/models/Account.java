package horse.models;

import java.time.LocalDateTime;

public abstract class Account {

    protected String accountType;
    protected String clientType;
    protected LocalDateTime created_at;
    protected double balance;

    public void withdraw(double amount) {
        if (amount > balance) throw new IllegalArgumentException("Saldo insuficiente na Conta.");
        this.balance -= amount;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
