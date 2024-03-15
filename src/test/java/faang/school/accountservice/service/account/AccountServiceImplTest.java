package faang.school.accountservice.service.account;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.numberGenerator.RandomNumberGenerator;
import faang.school.accountservice.service.owner.OwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static faang.school.accountservice.enums.AccountStatus.*;
import static faang.school.accountservice.enums.AccountType.DEBIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Nested
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OwnerServiceImpl ownerService;
    @Mock
    private RandomNumberGenerator randomNumberGenerator;
    @Mock
    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private Owner owner;
    private AccountDto accountDto;

    @BeforeEach
    void init() {
        owner = Owner.builder()
                .id(1L)
                .accounts(new ArrayList<>())
                .ownerType(OwnerType.USER)
                .build();

        account = Account.builder()
                .id(1L)
                .accountType(DEBIT)
                .number("123137654231324")
                .owner(owner)
                .status(ACTIVE)
                .build();

        accountDto = AccountDto.builder()
                .id(1L)
                .number("123137654231324")
                .type(DEBIT)
                .currency(Currency.RUB)
                .build();
    }

    @Test
    void testGetAccount() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.ofNullable(account));

        accountService.getAccount(anyLong());

        verify(accountRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreateAccount() {
        when(ownerService.findById(anyLong())).thenReturn(Optional.ofNullable(owner));
        when(accountMapper.toEntity(any(AccountDto.class))).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

        accountService.create(accountDto);

        verify(ownerService, times(1)).findById(anyLong());
        verify(accountMapper, times(1)).toEntity(any(AccountDto.class));
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(accountMapper, times(1)).toDto(any(Account.class));
    }

    @Test
    void testBlockAccountWhenRequested() {
        accountDto.setStatus(BLOCKED);
        when(accountRepository.findById((anyLong()))).thenReturn(Optional.of(account));
        when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

        accountService.blockAccount(account.getId());

        verify(accountMapper, times(1)).toDto(any(Account.class));
        assertEquals(BLOCKED, accountDto.getStatus());
    }

    @Test
    void closeAccount() {
        accountDto.setStatus(CLOSED);
        when(accountRepository.findById((anyLong()))).thenReturn(Optional.of(account));
        when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

        accountService.closeAccount(account.getId());

        verify(accountMapper, times(1)).toDto(any(Account.class));
        assertEquals(CLOSED, accountDto.getStatus());
    }

}