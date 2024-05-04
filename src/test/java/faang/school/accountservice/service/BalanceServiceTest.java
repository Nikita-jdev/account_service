package faang.school.accountservice.service;

import faang.school.accountservice.mapper.BalanceMapperImpl;
import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.model.balance.Balance;
import faang.school.accountservice.repository.BalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Spy
    private BalanceMapperImpl balanceMapper;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void createBalance_ValidArgs() {
        when(accountService.getAccountById(anyLong())).thenReturn(new Account());
        when(balanceRepository.save(any(Balance.class))).thenReturn(Balance.builder().id(1L).account(new Account()).build());
        BalanceDto balanceDto = getBalanceDto();

        balanceService.createBalance(balanceDto);

        verify(accountService, times(1)).getAccountById(anyLong());
        verify(balanceRepository, times(1)).save(any(Balance.class));
        verify(balanceMapper, times(1)).toDto(any(Balance.class));
    }

    @Test
    void updateBalance_ValidArgs() {
        when(balanceRepository.save(any(Balance.class))).thenReturn(Balance.builder().id(1L).account(new Account()).build());
        BalanceDto balanceDto = getBalanceDto();
        long balanceId = 1L;

        BalanceDto actualDto = balanceService.updateBalance(balanceId, balanceDto);

        assertEquals(balanceId, actualDto.getId());
        verify(balanceMapper, times(1)).toEntity(any(BalanceDto.class));
        verify(balanceMapper, times(1)).toDto(any(Balance.class));
    }

    @Test
    void getBalance_ValidArgs() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.of(Balance.builder().id(1L).account(new Account()).build()));

        balanceService.getBalance(1L);

        verify(balanceRepository, times(1)).findById(anyLong());
        verify(balanceMapper, times(1)).toDto(any(Balance.class));
    }

    @Test
    void getBalance_AccountDoesNotExists() {
        when(balanceRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> balanceService.getBalance(anyLong()));
    }

    private BalanceDto getBalanceDto() {
        return BalanceDto.builder().build();
    }
}
