package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.mapper.AccountMapperImpl;
import faang.school.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AccountMapper accountMapper = new AccountMapperImpl();
    @Mock
    private FreeAccountNumbersService freeAccountNumbersService;
@InjectMocks
private AccountService accountService;

private AccountDto accountDto;
private Account account;

@BeforeEach
void setUp() {
    accountDto = AccountDto.builder()
            .ownerId(1L)
            .accountType("DEBIT")
            .currency("USD")
            .build();

    account = Account.builder()
            .id(1L)
            .build();
}
    @Test
    void openAccount() {
    Mockito.when(freeAccountNumbersService.getFreeNumber(Mockito.any())).thenReturn("0000000000000000");
    Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);

    accountService.openAccount(accountDto);

    Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(freeAccountNumbersService, Mockito.times(1)).getFreeNumber(Mockito.any());
    }
}