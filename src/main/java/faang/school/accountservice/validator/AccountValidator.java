package faang.school.accountservice.validator;

import faang.school.accountservice.entity.Account;
import faang.school.accountservice.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidator {
    public void validateNotFrozen(Account account) {
        if (account.getStatus() == Status.FROZEN) {
            throw new IllegalStateException("Аккаунт уже заблокирован");
        }
    }
}
