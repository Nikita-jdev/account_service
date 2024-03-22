package faang.school.accountservice.service;

import faang.school.accountservice.entity.AccountSeq;
import faang.school.accountservice.entity.AccountType;
import faang.school.accountservice.entity.FreeAccountId;
import faang.school.accountservice.entity.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;
    private final FreeAccountNumbersRepository freeAccountNumbersRepository;

    @Transactional
    public void generateAccountNumbers(AccountType type, int batchSize, long pattern) {
        AccountSeq period = accountNumbersSequenceRepository.incrementCounter(type.name(), batchSize);
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        for (long i = period.getInitialValue(); i < period.getCounter(); i++) {
            freeAccountNumbers.add(new FreeAccountNumber(new FreeAccountId(type, pattern + i)));
        }
        freeAccountNumbersRepository.saveAll(freeAccountNumbers);
    }

    @Transactional
    public void retrieveAccountNumber(AccountType type, Consumer<FreeAccountNumber> numberConsumer) {
        numberConsumer.accept(freeAccountNumbersRepository.retrieveFirst(type.name()));
    }
}
