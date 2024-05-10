package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.account.numbers.AccountNumberSequence;
import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    @Value("${account.number.batch.size}")
    private int batchSize;

    private static final long CREDIT_PATTERN = 4200_0000_0000_0000L;
    private static final long DEBIT_PATTERN = 5236_0000_0000_0000L;

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    @Transactional
    public void generateAccountNumbers(AccountType type, int batchSize) {
        List<FreeAccountNumber> numbers = new ArrayList<>();
        AccountNumberSequence period = accountNumbersSequenceRepository.incrementCounter(type.name(), batchSize);
        if (type == AccountType.CREDIT) {
            for (long i = period.getInitialValue(); i < period.getCounter(); i++) {
                numbers.add(new FreeAccountNumber(new FreeAccountId(type, CREDIT_PATTERN + i)));
            }
        } else {
            for (long i = period.getInitialValue(); i < period.getCounter(); i++) {
                numbers.add(new FreeAccountNumber(new FreeAccountId(type, DEBIT_PATTERN + i)));
            }
            freeAccountNumbersRepository.saveAll(numbers);
        }
    }

    @Transactional
    public void retrieveAccountNumbers(AccountType type, Consumer<FreeAccountNumber> numberConsumer) {
        FreeAccountNumber accountNumber = freeAccountNumbersRepository.retrieveAndDeleteFirst(type.name());
        if (accountNumber == null) {
            generateAccountNumbers(type, batchSize);
        }
        numberConsumer.accept(freeAccountNumbersRepository.retrieveAndDeleteFirst(type.name()));
    }
}
