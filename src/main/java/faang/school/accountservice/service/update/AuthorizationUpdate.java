package faang.school.accountservice.service.update;

import faang.school.accountservice.enums.BalanceOperation;
import faang.school.accountservice.model.Balance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AuthorizationUpdate implements UpdateBalance {

    @Override
    public boolean isApplicable(String typeOperation) {
        return typeOperation.equalsIgnoreCase(String.valueOf(BalanceOperation.AUTHORIZATION));
    }

    @Override
    public Balance update(Balance balance, BigDecimal amount) {
        BigDecimal newAuthorizationBalance = balance.getAuthorizationBalance().subtract(amount);
        balance.setAuthorizationBalance(newAuthorizationBalance);
        return balance;
    }
}
