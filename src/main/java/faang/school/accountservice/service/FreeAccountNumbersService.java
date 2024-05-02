package faang.school.accountservice.service;

import faang.school.accountservice.model.account.numbers.AccountNumbersSequence;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FreeAccountNumbersService {

    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    public void generateAndProcessFreeAccountNumber(String accountType, AccountAction accountAction) {
        synchronized (accountType) {
            AccountNumbersSequence sequence = accountNumbersSequenceRepository.findById(Long.valueOf(accountType))
                    .orElseGet(() -> {
                        accountNumbersSequenceRepository.createSequenceForAccountType(accountType);
                        return new AccountNumbersSequence();
                    });
            long currentNumber = sequence.getCurrentNumber();

            do {
                currentNumber = accountNumbersSequenceRepository.incrementSequenceIfValueMatches(accountType, currentNumber) ? currentNumber + 1 : currentNumber;
            } while (!accountNumbersSequenceRepository.incrementSequenceIfValueMatches(accountType, currentNumber));

            String accountNumber = generateAccountNumber(accountType, currentNumber);

            try {
                FreeAccountNumber freeAccountNumber = new FreeAccountNumber(accountType, accountNumber);
                freeAccountNumbersRepository.save(freeAccountNumber);
                accountAction.perform(accountNumber);
            } catch (Exception e) {
                throw new RuntimeException("Failed to process account number: " + accountNumber, e);
            }
        }
    }

    private String generateAccountNumber(String accountType, Long currentNumber) {
        String accountNumberPrefix = getAccountNumberPrefix(accountType);
        String formattedCurrentNumber = String.format("%010d", currentNumber);
        return accountNumberPrefix + formattedCurrentNumber;
    }

    private String getAccountNumberPrefix(String accountType) {
        return switch (accountType) {
            case "DEBIT" -> "DEBIT-";
            case "SAVINGS" -> "SAVINGS-";
            default -> "";
        };
    }

    @FunctionalInterface
    public interface AccountAction {
        void perform(String accountNumber);
    }
}

