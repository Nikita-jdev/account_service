package faang.school.accountservice.service;

import faang.school.accountservice.entity.FreeAccountNumberId;
import faang.school.accountservice.entity.FreeAccountNumberSequence;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.entity.FreeAccountNumber;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import faang.school.accountservice.repository.FreeAccountNumbersSeqRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;

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

    @Retryable(retryFor = OptimisticLockException.class)
    @Transactional
    public void generateNumber(int count, AccountType accountType) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        FreeAccountNumberSequence seq = freeAccountNumbersSeqRepository
                .getFreeAccountNumberSequence(count, accountType.name());
        for (int i = 0; i < count; i++) {
            freeAccountNumbers.add(FreeAccountNumber.builder()
                    .id(new FreeAccountNumberId(accountType,
                            (String.valueOf(accountType.getPrefix() * 1000_0000_0000L + seq.getCount()))))
                    .build());
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    public void getNumber(AccountType type,
                          Consumer<FreeAccountNumber> freeAccountNumberConsumer) {
        freeAccountNumberConsumer.accept(freeAccountNumbersRepository.findFirstAndDeleteNumber(type.name()));
    }
}
