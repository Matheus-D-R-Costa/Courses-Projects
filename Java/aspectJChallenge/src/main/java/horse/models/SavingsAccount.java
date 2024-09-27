package horse.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SavingsAccount extends Account {

    public SavingsAccount(String clientType) {
        this.accountType = "Savings Account";
        this.created_at = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.clientType = clientType;
    }
}
