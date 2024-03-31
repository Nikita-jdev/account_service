package faang.school.accountservice.service.update;

import faang.school.accountservice.enums.BalanceOperation;
import faang.school.accountservice.model.Balance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ClearingUpdate implements UpdateBalance {

    @Override
    public boolean isApplicable(String typeOperation) {
        return typeOperation.equalsIgnoreCase(String.valueOf(BalanceOperation.CLEARING));
    }

    @Override
    public Balance update(Balance balance, BigDecimal amount) {
        BigDecimal newActualBalance = balance.getActualBalance().subtract(amount);
        balance.setActualBalance(newActualBalance);
        return balance;
    }
}
