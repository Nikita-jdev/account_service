package faang.school.accountservice.service.update;

import faang.school.accountservice.enums.BalanceOperation;
import faang.school.accountservice.model.Balance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DepositUpdate implements UpdateBalance {

    @Override
    public boolean isApplicable(String typeOperation) {
        return typeOperation.equalsIgnoreCase(String.valueOf(BalanceOperation.DEPOSIT));
    }

    @Override
    public Balance update(Balance balance, BigDecimal amount) {
        BigDecimal newAuthBalance = balance.getAuthorizationBalance().add(amount);
        BigDecimal newActualBalance = balance.getActualBalance().add(amount);
        balance.setAuthorizationBalance(newAuthBalance);
        balance.setActualBalance(newActualBalance);
        return balance;
    }
}
