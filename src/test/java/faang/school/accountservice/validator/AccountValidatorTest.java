package faang.school.accountservice.validator;

import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.enums.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountValidatorTest {
    private final AccountValidator accountValidator = new AccountValidator();
    private final Owner owner = Owner.builder().id(1L).build();
    private final Account account = Account.builder()
            .id(1L)
            .accountType(Type.DEBIT)
            .accountOwner(owner)
            .status(Status.ACTIVE)
            .build();

    @Test
    void validateNotFrozen() {
        assertDoesNotThrow(() -> accountValidator.validateNotFrozen(account));
    }

    @Test
    void validateFrozen(){
        account.setStatus(Status.FROZEN);
        assertThrows(IllegalStateException.class, () -> accountValidator.validateNotFrozen(account));
    }
}