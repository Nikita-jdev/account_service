package faang.school.accountservice.validation;

import faang.school.accountservice.client.ProjectServiceClient;
import faang.school.accountservice.client.UserServiceClient;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.model.Account;
import feign.FeignException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidate {
    private final UserServiceClient userServiceClient;
    private final ProjectServiceClient projectServiceClient;
    public void validate(Account account){
        checkOwner(account);
    }

    @Retryable(retryFor = ReadTimeoutException.class, backoff = @Backoff(delay = 2000))
    private void checkOwner(Account account) {
        OwnerType ownerType = account.getOwner().getOwnerType();
        long ownerId = account.getOwner().getAccountId();
        try {
            if (OwnerType.USER.equals(ownerType)) {
                userServiceClient.getUser(ownerId);
            } else if (OwnerType.PROJECT.equals(ownerType)) {
                projectServiceClient.getProject(ownerId);
            }
        } catch (FeignException ex) {
            throw new EntityNotFoundException(String.format("%s with id %d not found", ownerType, ownerId));
        }
    }

}
