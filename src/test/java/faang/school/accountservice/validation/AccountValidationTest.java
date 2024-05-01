package faang.school.accountservice.validation;

import faang.school.accountservice.client.ProjectServiceClient;
import faang.school.accountservice.client.UserServiceClient;
import faang.school.accountservice.dto.ProjectDto;
import faang.school.accountservice.dto.UserDto;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.model.Account;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountValidationTest {
    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private AccountValidate accountValidate;

    @Test
    public void testValidate_UserOwner_Exists() {
        Account account = new Account();
        account.setOwnerType(OwnerType.USER);
        account.setOwnerId(1L);

        when(userServiceClient.getUser(1L)).thenReturn(new UserDto());

        assertDoesNotThrow(() -> accountValidate.validate(account));
    }

    @Test
    public void testValidate_UserOwner_NotFound() {
        Account account = new Account();
        account.setOwnerType(OwnerType.USER);
        account.setOwnerId(1L);

        when(userServiceClient.getUser(1L)).thenThrow(FeignException.NotFound.class);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountValidate.validate(account));
        assertEquals("USER with id 1 not found", exception.getMessage());
    }

    @Test
    public void testValidate_ProjectOwner_Exists() {
        Account account = new Account();
        account.setOwnerType(OwnerType.PROJECT);
        account.setOwnerId(1L);

        when(projectServiceClient.getProject(1L)).thenReturn(new ProjectDto());

        assertDoesNotThrow(() -> accountValidate.validate(account));
    }

    @Test
    public void testValidate_ProjectOwner_NotFound() {
        Account account = new Account();
        account.setOwnerType(OwnerType.PROJECT);
        account.setOwnerId(1L);

        when(projectServiceClient.getProject(1L)).thenThrow(FeignException.NotFound.class);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountValidate.validate(account));
        assertEquals("PROJECT with id 1 not found", exception.getMessage());
    }
}
