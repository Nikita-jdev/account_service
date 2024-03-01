package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumberSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {
    private final FreeAccountNumberRepository freeAccountNumberRepository;
    private final AccountNumberSequenceRepository accountNumberSequenceRepository;

    private static final int OFFSET_NUMBER_TO_GENERATE = 1;
    private static final int COUNT_ATTEMPTS = 2;

    @Transactional
    public String getFreeNumber(AccountType type) {

        if (!freeAccountNumberRepository.existsByAccountType(type)) {
            generateFreeAccountNumbers(type, 1);
        }
        FreeAccountNumber freeAccountNumber = freeAccountNumberRepository.getAndDeleteFirstByAccountType(type.toString()).orElse(null);
        String accountNumber = freeAccountNumber.getAccountNumber();
        return accountNumber;
    }

    @Transactional
    public void generateFreeAccountNumbers(AccountType type, int batchSize) {
        log.info("Start generation account numbers for account type {}", type);
        accountNumberSequenceRepository.createSequenceIfNecessary(type.toString());
        updateCurrentSequence(type, batchSize);
        long currentSequence = accountNumberSequenceRepository.findByAccountType(type).getCounter();
        long firstNumberToGenerate = currentSequence - batchSize + OFFSET_NUMBER_TO_GENERATE;
        List<FreeAccountNumber> numbers = new ArrayList<>();

        for (long i = firstNumberToGenerate; i <= currentSequence; i++) {
            FreeAccountNumber number = new FreeAccountNumber(type.getPrefixNumber() + String.format("%012d", i), type);
            numbers.add(number);
        }
        freeAccountNumberRepository.saveAll(numbers);
        log.info("Finish of generating and saving {} free numbers for account type - {}", numbers.size(), type);
    }

    private void updateCurrentSequence(AccountType type, int batchSize) {
        int currentSequence = 0;
        int attempt = 0;
        // попытки обращения к БД для обновления счетчика
        while (attempt < COUNT_ATTEMPTS) {
            currentSequence = accountNumberSequenceRepository.incrementCounter(type.toString(), batchSize);
            if (currentSequence != 0) {
                break;
            }
            if (attempt == COUNT_ATTEMPTS) {
                throw new RuntimeException("Не удается обновить счетчик для счетов типа " + type);
            }
            attempt++;
        }
    }

    @Async("executorService")
    @Transactional
    public void scheduledGenerateFreeNumbers(AccountType type, int batchSize) {
        int currentCountNumbers = freeAccountNumberRepository.countByAccountType(type);
        Integer toGenerateCount = batchSize - currentCountNumbers;
        if (Integer.signum(toGenerateCount) < 1) {
            return;
        }
        generateFreeAccountNumbers(type, toGenerateCount);
    }
}