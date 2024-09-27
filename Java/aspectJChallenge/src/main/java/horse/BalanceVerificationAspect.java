package horse;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class BalanceVerificationAspect {

    @Before("execution(* horse.models.Account.withdraw(..)) && args(amount)")
    public void verifyBalance(double amount) {
        // Mensagem antes da operação de saque
        System.out.println("Verifying balance for withdrawal: $" + amount);
    }
}

