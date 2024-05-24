package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.account.numbers.AccountNumberSequence;
import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FreeAccountNumberServiceTest {

    @InjectMocks
    private FreeAccountNumbersService freeAccountNumberService;
    @Mock
    private AccountNumbersSequenceRepository accountSequenceRepository;
    @Mock
    private FreeAccountNumbersRepository freeAccountNumbersRepository;

    @Test
    void testGenerateAccountNumbers() {
        AccountType accountType = AccountType.CREDIT;
        int batchSize = 100;
        AccountNumberSequence accountNumberSequence = new AccountNumberSequence();
        accountNumberSequence.setType(accountType);
        accountNumberSequence.setCounter(100);


        when(accountSequenceRepository.incrementCounter(accountType.name(), batchSize))
                .thenReturn(accountNumberSequence);

        freeAccountNumberService.generateAccountNumbers(accountType, batchSize);

        verify(accountSequenceRepository, times(1))
                .incrementCounter(accountType.name(), batchSize);
    }

    @Test
    void testRetrieveAccountNumbers() {
        AccountType accountType = AccountType.DEBIT;
        Consumer<FreeAccountNumber> consumer = Mockito.mock(Consumer.class);

        when(freeAccountNumbersRepository.retrieveAndDeleteFirst(accountType.name()))
                .thenReturn(new FreeAccountNumber(new FreeAccountId(accountType, 4200_0000_0000_0000L)));

        freeAccountNumberService.retrieveAccountNumbers(accountType, consumer);

        verify(freeAccountNumbersRepository, times(2)).retrieveAndDeleteFirst(accountType.name());
        verify(consumer, times(1)).accept(Mockito.any(FreeAccountNumber.class));
    }
}