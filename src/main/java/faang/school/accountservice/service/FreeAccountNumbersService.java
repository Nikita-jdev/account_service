package faang.school.accountservice.service;

import faang.school.accountservice.model.account.numbers.AccountNumberSequence;
import faang.school.accountservice.model.account.numbers.AccountType;
import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    private static final long PATTERN = 4200_0000_0000_0000L;

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    @Transactional
    public void generateAccountNumbers(AccountType type, int batchSize){
        AccountNumberSequence period = accountNumbersSequenceRepository.incrementCounter(type.name(), batchSize);
        List<FreeAccountNumber> numbers = new ArrayList<>();
        for (long i = period.getInitialValue(); i < period.getCounter(); i++) {
            numbers.add(new FreeAccountNumber(new FreeAccountId(type, PATTERN + i)));
        }
        freeAccountNumbersRepository.saveAll(numbers);
    }

    @Transactional
    public void retrieveAccountNumber(AccountType type, Consumer<FreeAccountNumber> numberConsumer){
        numberConsumer.accept(freeAccountNumbersRepository.retrieveAndDeleteFirst(type.name()));
    }
}
