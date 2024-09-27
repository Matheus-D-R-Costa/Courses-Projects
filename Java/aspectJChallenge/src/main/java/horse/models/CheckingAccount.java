package horse.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CheckingAccount extends Account {

    public CheckingAccount(String clientType) {
        this.accountType = "Checking Account";
        this.created_at = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        this.clientType = clientType;
    }
}
