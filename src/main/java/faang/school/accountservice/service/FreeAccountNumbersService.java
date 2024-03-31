package faang.school.accountservice.service;

import faang.school.accountservice.entity.FreeAccountNumber;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import faang.school.accountservice.repository.FreeAccountNumbersSeqRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final FreeAccountNumbersSeqRepository freeAccountNumbersSeqRepository;
    private static final long NUMBER_PREFIX = 1000_0000_0000_0L;

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, backoff = @Backoff(delay = 100))
    public void generateNumber(int batchSize, AccountType accountType) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        long seq = freeAccountNumbersSeqRepository.getSeqByType(accountType.name());
        int updateSuccess = freeAccountNumbersSeqRepository.incrementCounter(accountType.name(), batchSize);
        if (updateSuccess == 1) {
            for (long i = seq + 1; i <= batchSize; i++) {
                freeAccountNumbers.add(FreeAccountNumber.builder()
                        .accountNumber(String.valueOf(accountType.getPrefix() * NUMBER_PREFIX + i))
                        .accountType(accountType)
                        .build());
            }
        } else {
            throw new OptimisticLockException();
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    public String getNumber(AccountType type) {
        String freeAccountNumber = freeAccountNumbersRepository.findFirstAndDeleteNumber(type.name());

        return freeAccountNumber;
    }
}
