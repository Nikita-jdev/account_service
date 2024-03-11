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

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, backoff = @Backoff(delay = 100))
    public void generateNumber(int batchSize, AccountType accountType) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        long seq = freeAccountNumbersSeqRepository.getSeqByType(accountType.name());
        freeAccountNumbersSeqRepository.incrementCounter(accountType.name(), batchSize);
        for (long i = seq + 1; i <= batchSize; i++) {
            freeAccountNumbers.add(FreeAccountNumber.builder()
                    .accountNumber(String.valueOf(accountType.getPrefix() * 1000_0000_0000_0L + i))
                    .accountType(accountType)
                    .build());
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    public void getNumber(AccountType type, Consumer<String> consumer) {
        String freeAccountNumber = freeAccountNumbersRepository.findFirstAndDeleteNumber(type.name());
        consumer.accept(freeAccountNumber);
    }
}