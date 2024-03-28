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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final AccountRepository accountRepository;
    private final BalanceMapper balanceMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {OptimisticLockException.class}, maxAttempts = 5)
    public BalanceDto createBalance(Long ownerId) {
        log.info("Start creating balance");
        Account account = accountRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by ID "+ownerId));
        log.info("before creating balance");
        Balance balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .clearingBalance(BigDecimal.ZERO)
                .build();
        log.info("after creating balance");

        balanceRepository.save(balance);
        log.info("Balance is created");
        return balanceMapper.toDto(balance);
    }

    public BalanceDto deleteBalance(String accountNumber) {
        Balance balance = balanceRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Balance not found by account number"));
        BalanceDto balanceDto = balanceMapper.toDto(balance);
        balanceRepository.delete(balance);
        return balanceDto;
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
