package faang.school.accountservice.service.balance;

import static faang.school.accountservice.enums.AccountStatus.ACTIVE;
import static faang.school.accountservice.enums.AccountType.DEBIT;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {
    @InjectMocks
    private BalanceService balanceService;
    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BalanceMapper balanceMapper;

    private Owner owner;
    private Account account;
    private Balance balance;
    @BeforeEach
    void setUp() {
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

        balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .clearingBalance(BigDecimal.ZERO)
                .build();
    }

    @Test
    void testAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> balanceService.createBalance(1L));
        assertEquals("Account not found by ID "+1L, e.getMessage());
    }

    @Test
    void testBalanceIsSaved() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        balanceService.createBalance(1L);
        verify(balanceRepository, times(1)).save(balance);
    }

    @Test
    void testBalanceIsUpdated() {
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balance));
        balanceService.updateBalance(1L, BigDecimal.valueOf(123.4));
        assertEquals(BigDecimal.valueOf(123.4), balance.getAuthorizationBalance());
        assertEquals(BigDecimal.valueOf(123.4), balance.getClearingBalance());
    }
    @Test
    void testBalanceNotFound() {
        when(balanceRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> balanceService.updateBalance(1L, BigDecimal.ONE));
        assertEquals("Balance not found by ID "+1L, e.getMessage());
    }

    @Test
    void testGetBalance() {
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balance));
        balanceService.getBalance(1L);
        verify(balanceMapper, times(1)).toDto(balance);
    }
}
