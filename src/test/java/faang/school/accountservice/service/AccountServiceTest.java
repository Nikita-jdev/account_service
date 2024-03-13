package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.CreateAccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Type;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.mapper.AccountMapperImpl;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validator.AccountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();
    @Mock
    private FreeAccountNumbersService freeAccountNumbersService;
    @Mock
    private OwnerService ownerService;
    @Mock
    private AccountValidator accountValidator;
    @InjectMocks
    private AccountService accountService;

    private AccountDto accountDto;
    private Account account;
    private CreateAccountDto createAccountDto;

    @BeforeEach
    void setUp() {
        accountDto = AccountDto.builder()
                .ownerId(1L)
                .accountType(Type.DEBIT)
                .currency(Currency.USD)
                .build();

        account = Account.builder()
                .id(1L)
                .build();

        createAccountDto = CreateAccountDto.builder()
                .ownerId(1L)
                .accountType(Type.DEBIT)
                .currency(Currency.USD)
                .build();
    }

    @Test
    void openAccountTest() {
        Mockito.when(freeAccountNumbersService.getFreeNumber(Mockito.any())).thenReturn("0000000000000000");
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        Mockito.when(ownerService.findById(1L)).thenReturn(new Owner());
        accountService.openAccount(createAccountDto);

        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(freeAccountNumbersService, Mockito.times(1)).getFreeNumber(Mockito.any());
    }

    @Test
    void getAccountTest() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        AccountDto foundAccount = accountService.getAccount(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
        assertEquals(1L, foundAccount.getId());
    }

    @Test
    void blockAccountTest() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        accountService.blockAccount(1L);
        Mockito.verify(accountValidator, Mockito.times(1)).validateNotFrozen(account);
        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void closeAccountTest() {
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        accountService.closeAccount(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    }
}