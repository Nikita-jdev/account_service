package faang.school.accountservice.service;

import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    private final FreeAccountNumberRepository freeAccountNumberRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    public String generateNewAccountNumber(String accountType) {
        FreeAccountNumber freeAccountNumber = freeAccountNumberRepository.getAndDeleteFirstFreeAccountNumber();

        if (freeAccountNumber != null) {
            return freeAccountNumber.getAccountNumber();
        }

        accountNumbersSequenceRepository.incrementSequenceIfValueMatches(accountType, 0); // Инкремент с 0
        long nextNumber = accountNumbersSequenceRepository.getCurrentNumber(accountType); // Получить текущее значение счетчика
        String accountNumber = accountType + String.format("%012d", nextNumber); // Форматирование номера

        freeAccountNumberRepository.create(nextNumber, accountType);

        return accountNumber;
    }
}