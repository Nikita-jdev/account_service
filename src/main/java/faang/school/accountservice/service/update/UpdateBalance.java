package faang.school.accountservice.service.update;

import faang.school.accountservice.model.Balance;

import java.math.BigDecimal;

public interface UpdateBalance {

    boolean isApplicable(String typeOperation);

    Balance update(Balance balance, BigDecimal amount);
}
