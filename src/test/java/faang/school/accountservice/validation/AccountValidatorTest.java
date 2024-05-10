package faang.school.accountservice.validation;

import faang.school.accountservice.exception.DataValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountValidatorTest {
    private AccountValidator validator = new AccountValidator();

    @Test
    void validateAccountOwner_UserIsOwner_ShouldNotThrow() {
        assertDoesNotThrow(() -> validator.validateAccountOwner(10L, 10L));
    }

    @Test
    void validateAccountOwner_UserIsNotOwner_ShouldThrowDataValidationException() {
        assertThrows(DataValidationException.class,
                () -> validator.validateAccountOwner(1L, 2L)
        );
    }
}
