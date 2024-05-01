package faang.school.accountservice.validation;

import faang.school.accountservice.client.ProjectServiceClient;
import faang.school.accountservice.client.UserServiceClient;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.model.Account;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidate {
    private final UserServiceClient userServiceClient;
    private final ProjectServiceClient projectServiceClient;
    public void validate(Account account){
        checkOwner(account);
    }

    private void checkOwner(Account account) {
        try {
            if (OwnerType.USER.equals(account.getOwnerType())) {
                userServiceClient.getUser(account.getOwnerId());
            } else if (OwnerType.PROJECT.equals(account.getOwnerType())) {
                projectServiceClient.getProject(account.getOwnerId());
            }
        } catch (FeignException ex) {
            throw new EntityNotFoundException(String.format("%s with id %d not found", account.getOwnerType(), account.getOwnerId()));
        }
    }

}
