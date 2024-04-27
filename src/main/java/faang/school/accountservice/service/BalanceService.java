package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.model.balance.Balance;
import faang.school.accountservice.repository.BalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceMapper balanceMapper;
    private final BalanceRepository balanceRepository;
    private final AccountService accountService;

    @Transactional
    public BalanceDto createBalance(BalanceDto balanceDto) {
        Account account = accountService.getAccountById(balanceDto.getAccountId());
        Balance balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.ZERO)
                .actualBalance(BigDecimal.ZERO)
                .build();
        Balance savedBalance = balanceRepository.save(balance);
        log.info("Balance created: {}", savedBalance);
        return balanceMapper.toDto(savedBalance);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 5000, multiplier = 2))
    public BalanceDto updateBalance(long balanceId, BalanceDto balanceDto) {
        Balance balance = balanceMapper.toEntity(balanceDto);
        balance.setId(balanceId);
        log.info("Balance updated: {}", balance);
        return balanceMapper.toDto(balanceRepository.save(balance));
    }

    @Transactional(readOnly = true)
    public BalanceDto getBalance(long accountId) {
        Balance balance = balanceRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Balance with id %d does not exists", accountId)));
        return balanceMapper.toDto(balance);
    }
}
