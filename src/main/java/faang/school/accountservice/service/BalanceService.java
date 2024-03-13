package faang.school.accountservice.service;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.mapper.BalanceMapper;
import faang.school.accountservice.model.Balance;
import faang.school.accountservice.repository.BalanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;

    public BalanceDto createBalance() {
        Balance balance = new Balance();
        return balanceMapper.toDto(balanceRepository.save(balance));
    }

    public Balance updateBalance(String accountNumber, BalanceDto balanceDto) {
        Balance balance = getBalance(accountNumber);

        if (balance.getVersion() == balanceDto.getVersion()) {
            Balance updatedBalance = balanceMapper.toEntity(balanceDto);
            updatedBalance.setCreatedAt(balance.getCreatedAt());
            updatedBalance.versionIncrement();
            return balanceRepository.save(updatedBalance);
        } else {
            throw new ConcurrentModificationException("Balance has been modified");
        }
    }

    public BigDecimal authorizePayment(String accountNumber, BigDecimal amount) {
        Balance balance = getBalance(accountNumber);
        BigDecimal authorizationBalance = balance.getAuthorizationBalance();

        if (balance.getActualBalance().compareTo(amount) >= 0) {
            BigDecimal newAuthorizationBalance = authorizationBalance.subtract(amount);
            balance.setAuthorizationBalance(newAuthorizationBalance);
            balanceRepository.save(balance);
            return newAuthorizationBalance;
        } else {
            cancelAuthorization(accountNumber, amount);
            return authorizationBalance;
        }
    }

    public void finalizePayment(String accountNumber, BigDecimal amount) {
        Balance balance = getBalance(accountNumber);
    }

    public void cancelAuthorization(String accountNumber, BigDecimal amount) {

    }

    public Balance getBalance(String accountNumber) {
        return balanceRepository.findByAccount_Number(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Balance not found"));
    }

}
