package faang.school.accountservice.service;

import faang.school.accountservice.entity.AccountSeq;
import faang.school.accountservice.entity.AccountType;
import faang.school.accountservice.entity.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FreeAccountNumbersServiceTest {

    @InjectMocks
    private FreeAccountNumbersService freeAccountNumbersService;
    @Mock
    private AccountNumbersSequenceRepository accountNumbersSequenceRepository;
    @Mock
    private FreeAccountNumbersRepository freeAccountNumbersRepository;
    @Captor
    ArgumentCaptor<List<FreeAccountNumber>> captor;

    @Test
    void testGenerateAccountNumbersSuccessful() {
        when(accountNumbersSequenceRepository.incrementCounter(AccountType.DEBIT.name(), 111))
                .thenReturn(new AccountSeq());
        freeAccountNumbersService.generateAccountNumbers(AccountType.DEBIT, 111, 1);
        verify(freeAccountNumbersRepository).saveAll(captor.capture());
    }

    @Test
    void testRetrieveAccountNumbersSuccessful() {
        Consumer<FreeAccountNumber> numberConsumer = mock(Consumer.class);
        freeAccountNumbersService.retrieveAccountNumber(AccountType.DEBIT, numberConsumer);
        verify(freeAccountNumbersRepository).retrieveFirst(AccountType.DEBIT.name());
    }
}
