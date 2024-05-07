package faang.school.accountservice.service;

import faang.school.accountservice.dto.account.AccountDto;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.mapper.account.AccountMapperImpl;
import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.model.account.AccountStatus;
import faang.school.accountservice.model.account.AccountType;
import faang.school.accountservice.model.owner.Owner;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validation.AccountValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AccountMapperImpl accountMapper;
    @Mock
    private AccountValidator accountValidator;
    @InjectMocks
    AccountService accountService;

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(1L)
                .number("1234567890123")
                .owner(Owner.builder().id(10L).build())
                .accountType(AccountType.INDIVIDUAL)
                .currency(Currency.USD)
                .build();
        accountDto = AccountDto.builder()
                .id(account.getId())
                .number(account.getNumber())
                .ownerId(account.getOwner().getId())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .build();
    }

    @Test
    void open_AccountIsOpened_ThenReturnedAsDto() {
        account.setAccountStatus(AccountStatus.ACTIVE);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto returned = accountService.open(10L, accountDto);

        assertAll(
                () -> verify(accountMapper, times(1)).toEntity(accountDto),
                () -> verify(accountRepository, times(1)).save(any(Account.class)),
                () -> verify(accountMapper, times(1)).toDto(account),
                () -> assertEquals(accountDto, returned)
        );
    }

    @Test
    void get_AccountFound_ThenReturnedAsDto() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));

        AccountDto returned = accountService.get(10L, 1L);

        assertAll(
                () -> verify(accountRepository, times(1)).findById(1L),
                () -> verify(accountMapper, times(1)).toDto(account),
                () -> assertEquals(accountDto, returned)
        );
    }

    @Test
    void getAllOwnerAccounts_AccountsFoundThenReturnedAsDto() {
        when(accountRepository.findByOwnerId(10L)).thenReturn(List.of(account));

        List<AccountDto> returned = accountService.getAllOwnerAccounts(10L);

        assertAll(
                () -> verify(accountRepository, times(1)).findByOwnerId(10L),
                () -> verify(accountMapper, times(1)).toDto(List.of(account)),
                () -> assertEquals(List.of(accountDto), returned)
        );
    }

    @Test
    void block_AccountIsBlocked_Valid() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));

        accountService.block(10L, 1L);

        assertAll(
                () -> verify(accountRepository, times(1)).findById(1L),
                () -> assertEquals(AccountStatus.INACTIVE, account.getAccountStatus())
        );
    }

    @Test
    void close_AccountIsClosed_Valid() {
        when(accountRepository.findById(account.getId())).thenReturn(Optional.ofNullable(account));

        accountService.close(10L, 1L);

        assertAll(
                () -> verify(accountRepository, times(1)).findById(1L),
                () -> assertEquals(AccountStatus.CLOSED, account.getAccountStatus())
        );
    }
}
