package faang.school.accountservice.validation;

import faang.school.accountservice.client.ProjectServiceClient;
import faang.school.accountservice.client.UserServiceClient;
import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.OwnerDto;
import faang.school.accountservice.dto.ProjectDto;
import faang.school.accountservice.dto.UserDto;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.model.Owner;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Account account = getAccount();
        Owner owner = account.getOwner();

        when(userServiceClient.getUser(owner.getAccountId())).thenReturn(new UserDto());

        assertDoesNotThrow(() -> accountValidate.validate(account));
        verify(userServiceClient, times(1)).getUser(owner.getAccountId());
    }

    @Test
    public void testValidate_UserOwner_NotFound() {
        Account account = getAccount();
        Owner owner = account.getOwner();

        when(userServiceClient.getUser(owner.getAccountId())).thenThrow(FeignException.NotFound.class);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountValidate.validate(account));
        assertEquals("USER with id 1 not found", exception.getMessage());
    }

    @Test
    public void testValidate_ProjectOwner_Exists() {
        Account account = getAccount();
        Owner owner = account.getOwner();
        owner.setOwnerType(OwnerType.PROJECT);

        when(projectServiceClient.getProject(owner.getAccountId())).thenReturn(new ProjectDto());

        assertDoesNotThrow(() -> accountValidate.validate(account));
        verify(projectServiceClient, times(1)).getProject(owner.getAccountId());
    }

    @Test
    public void testValidate_ProjectOwner_NotFound() {
        Account account = getAccount();
        Owner owner = account.getOwner();
        owner.setOwnerType(OwnerType.PROJECT);

        when(projectServiceClient.getProject(owner.getAccountId())).thenThrow(FeignException.NotFound.class);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> accountValidate.validate(account));
        assertEquals("PROJECT with id 1 not found", exception.getMessage());
    }

    private AccountDto getAccountDto(){
        return AccountDto.builder()
                .owner(getOwnerDto())
                .number("123456789012345")
                .currency(Currency.USD)
                .accountType(AccountType.INDIVIDUAL)
                .build();
    }

    private OwnerDto getOwnerDto(){
        return OwnerDto.builder()
                .accountId(1L)
                .id(1)
                .ownerType(OwnerType.USER)
                .build();
    }

    private Owner getOwner(){
        return Owner.builder()
                .accountId(1L)
                .id(1)
                .ownerType(OwnerType.USER)
                .build();
    }

    private Account getAccount(){
        return Account.builder()
                .owner(getOwner())
                .number("123456789012345")
                .currency(Currency.USD)
                .accountType(AccountType.INDIVIDUAL)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
    }
}
