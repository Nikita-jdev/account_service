package faang.school.accountservice.service;

import faang.school.accountservice.entity.AccountNumber;
import faang.school.accountservice.entity.AccountNumberId;
import faang.school.accountservice.entity.AccountSequence;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {
    private final static long DEBIT = 4200_0000_0000_0000L;
    private final static long CREDIT = 5236_0000_0000_0000L;

    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;

    @Transactional
    public void generatedNumbers(String type, int batchSize) {
        AccountSequence accountSequence = accountNumbersSequenceRepository.incrementCounter(type, batchSize);
        long start = accountSequence.getInitialValue();
        long end = accountSequence.getCounter();
        long typeNumber = getTypeNumber(type);
        List<AccountNumber> accountNumbers = new ArrayList<>();
        for (long i = start; i < end; i++) {
            AccountNumberId accountNumberId = new AccountNumberId(typeNumber + i, type);
            AccountNumber accountNumber = new AccountNumber(accountNumberId);
            accountNumbers.add(accountNumber);
        }
        freeAccountNumbersRepository.saveAll(accountNumbers);
    }

    private long getTypeNumber(String type) {
        if (type.equals("debit")) {
            return DEBIT;
        } else {
            return CREDIT;
        }
    }
}
