package faang.school.accountservice.validation;

import faang.school.accountservice.exception.DataValidationException;
import org.springframework.stereotype.Component;

@Component
public class AccountValidator {

    public void validateAccountOwner(long userId, long ownerId) {
        if (userId != ownerId) {
            throw new DataValidationException(String.format("User with id %d is not the owner of the account", userId));
        }
    }
}
