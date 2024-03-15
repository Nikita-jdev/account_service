package faang.school.accountservice.service.balance;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final AccountRepository accountRepository;
    private final BalanceMapper balanceMapper;

    @Transactional
    @Retryable(retryFor = {OptimisticLockException.class}, maxAttempts = 4)
    public BalanceDto createBalance(Long accountId) {
        log.info("Start creating balance");
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by ID "+accountId));

        Balance balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .clearingBalance(BigDecimal.ZERO)
                .build();

        balanceRepository.save(balance);
        log.info("Balance is created");
        return balanceMapper.toDto(balance);
    }

    @Transactional
    public BalanceDto updateBalance(Long balanceId, BigDecimal deposit) {
        Balance balance = getBalanceById(balanceId);
        BigDecimal authorizationBalance = balance.getAuthorizationBalance();
        BigDecimal clearingBalance = balance.getClearingBalance();

        balance.setAuthorizationBalance(authorizationBalance.add(deposit));
        balance.setClearingBalance(clearingBalance.add(deposit));
        balance.setVersion(balance.getVersion() + 1);

        log.info("Balance is updated");
        return balanceMapper.toDto(balance);
    }

    @Transactional(readOnly = true)
    public BalanceDto getBalance(Long balanceId) {
        Balance balance = getBalanceById(balanceId);
        return balanceMapper.toDto(balance);
    }

    private Balance getBalanceById(Long balanceId) {
        return balanceRepository.findById(balanceId)
                .orElseThrow(() -> new EntityNotFoundException("Balance not found by ID "+balanceId));
    }
}
