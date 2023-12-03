package faang.school.accountservice.service;

import faang.school.accountservice.config.account.AccountGenerationConfig;
import faang.school.accountservice.entity.account.numbers.FreeAccountNumber;
import faang.school.accountservice.entity.account.AccountType;
import faang.school.accountservice.exception.NoFreeAccountNumbersException;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FreeAccountNumbersService {
    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;
    private final AccountGenerationConfig accountGenerationConfig;


    @Transactional
    public void generateAccountNumbersOfType(long numberOfAccounts, AccountType accountType) {
        createListAccountNumbers(numberOfAccounts, accountType);
    }

    @Transactional
    public void generateAccountNumbersToReach(long targetCount, AccountType accountType) {

        long currentCount = freeAccountNumbersRepository.countByAccountType(accountType);
        long batchSize = targetCount - currentCount;

        if (batchSize > 0) {
            createListAccountNumbers(batchSize, accountType);
        }
    }

    private void createListAccountNumbers(long batchSize, AccountType accountType) {
        int accountNumberLength = accountGenerationConfig.getAccountNumberLength();

        List<String> newAccountNumbers = generateAccountNumber(accountType, accountNumberLength, batchSize);

        List<FreeAccountNumber> accountNumbers = newAccountNumbers.stream()
                .map(accountNumber -> FreeAccountNumber.builder()
                        .accountType(accountType)
                        .accountNumber(accountNumber)
                        .createdAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        freeAccountNumbersRepository.saveAll(accountNumbers);
    }

    private List<String> generateAccountNumber(AccountType accountType, int length, long batchSize) {
        String prefix = accountType.getFirstNumberOfAccount();

        long currentCount = getOrCreateSequence(accountType);
        accountNumbersSequenceRepository.incrementByAccountType(accountType.name(), batchSize);

        return LongStream.range(1, batchSize + 1)
                .mapToObj(i -> prefix + String.format("%0" + (length - prefix.length()) + "d", currentCount + i))
                .collect(Collectors.toList());
    }

    public Long getOrCreateSequence(AccountType accountType) {
        return accountNumbersSequenceRepository
                .getCurrentCountByAccountType(accountType.name())
                .orElseGet(() -> {
                    accountNumbersSequenceRepository.createAccountNumberSequence(accountType.name());
                    return accountNumbersSequenceRepository
                            .getCurrentCountByAccountType(accountType.name())
                            .orElse(null);
                });
    }

    @Transactional
    public String getFreeAccountNumber(AccountType accountType) {
        try {
            Optional<String> accountNumber = findFirstFreeAccountNumber(accountType);
            if (accountNumber.isPresent()) {
                return accountNumber.get();
            }
        } catch (OptimisticLockingFailureException ole) {
            log.warn("Optimistic locking failure while getting free account number: {}", ole.getMessage());
        } catch (Exception e) {
            log.error("Error while getting free account number: {}", e.getMessage());
        }
        throw new NoFreeAccountNumbersException("No free account numbers even after generating additional numbers.");
    }

    private Optional<String> findFirstFreeAccountNumber(AccountType accountType) {
        Optional<String> accountNumber = freeAccountNumbersRepository.deleteAndReturnFirstByAccountTypeOrderByCreatedAtAsc(accountType.name());
        if (accountNumber.isEmpty()) {
            generateAdditionalAccountNumbers(accountType);
            log.warn("No free account numbers. Generated additional account numbers for {}.", accountType);
            accountNumber = freeAccountNumbersRepository.deleteAndReturnFirstByAccountTypeOrderByCreatedAtAsc(accountType.name());
        }
        return accountNumber;
    }

    private void generateAdditionalAccountNumbers(AccountType accountType) {
        int additionalNumbersToGenerate = 10;
        createListAccountNumbers(additionalNumbersToGenerate, accountType);
    }
}