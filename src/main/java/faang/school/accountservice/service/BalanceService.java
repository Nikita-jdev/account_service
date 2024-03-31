package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.exception.InsufficientFundsException;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.model.Balance;
import faang.school.accountservice.repository.BalanceRepository;
import faang.school.accountservice.service.update.UpdateBalance;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final List<UpdateBalance> balanceUpdaters;

    @Transactional
    public BalanceDto createBalance(Currency currency) {
        Balance balance = new Balance();
        balance.setCurrency(currency);
        return balanceMapper.toDto(saveBalance(balance));
    }

    @Transactional
    public BalanceDto deposit(String accountNumber, BigDecimal amount) {
        Balance balance = getBalance(accountNumber);
        Balance updatedBalance = updateBalance(balance, amount, "DEPOSIT");

        log.info("Deposit was successful an account: {}", accountNumber);
        return balanceMapper.toDto(updatedBalance);
    }

    private Balance updateBalance(Balance balance, BigDecimal amount, String balanceOperation) {
        for (UpdateBalance updater : balanceUpdaters) {
            if (updater.isApplicable(balanceOperation)) {
                updater.update(balance, amount);
                return saveBalance(balance);
            }
        }
        return saveBalance(balance);
    }

    public void authorizePayment(Balance balance, BigDecimal amount) {
        if (balance.getAuthorizationBalance().compareTo(amount) >= 0) {
            updateBalance(balance, amount, "AUTHORIZATION");
            log.info("Payment authorization was successful an account: {}", balance.getAccount());
        } else {
            log.info("Insufficient funds. Payment unsuccessful an account: {}", balance.getAccount());
            throw new InsufficientFundsException("Insufficient funds on account!");
        }
    }

    public void clearingPayment(Balance balance, BigDecimal amount) {
        BigDecimal authorizationBalance = balance.getAuthorizationBalance();
        BigDecimal actualBalance = balance.getActualBalance();

        if (authorizationBalance.compareTo(actualBalance) <= 0) {
            updateBalance(balance, amount, "CLEARING");
            log.info("Payment was successful an account: {}", balance.getAccount());
        } else {
            log.info("Payment was unsuccessful an account: {}", balance.getAccount());
        }
    }

    public void cancelAuthorization(String accountNumber, BigDecimal amount) {
        Balance balance = getBalance(accountNumber);
        BigDecimal newAuthorizationBalance = balance.getAuthorizationBalance().add(amount);
        balance.setAuthorizationBalance(newAuthorizationBalance);
        saveBalance(balance);
        log.info("Payment authorization has been cancelled an account: {}", accountNumber);
    }

    public Balance getBalance(String accountNumber) {
        return balanceRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Balance not found"));
    }

    private Balance saveBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

}
