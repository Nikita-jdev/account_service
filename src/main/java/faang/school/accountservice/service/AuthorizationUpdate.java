package faang.school.accountservice.service;

import faang.school.accountservice.enums.BalanceOperation;
import faang.school.accountservice.model.Balance;

import java.math.BigDecimal;

public class AuthorizationUpdate implements Updater<BalanceOperation, Balance, BigDecimal>{
    @Override
    public boolean isApplicable(BalanceOperation type) {
        return type.equals(BalanceOperation.AUTHORIZATION);
    }

    @Override
    public Balance update(Balance balance, BigDecimal amount) {
        return balance.setAuthorizationBalance(amount);
    }
}
