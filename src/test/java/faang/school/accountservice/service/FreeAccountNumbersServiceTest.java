package faang.school.accountservice.service;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.AccountNumberSequence;
import faang.school.accountservice.model.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumberSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FreeAccountNumbersServiceTest {

    @Mock
    private FreeAccountNumberRepository freeAccountNumberRepository;
    @Mock
    private AccountNumberSequenceRepository accountNumberSequenceRepository;
    @InjectMocks
    private FreeAccountNumbersService service;
    private AccountType type = AccountType.DEBIT;

    @Test
    void testGetFreeNumber() {
        //Arrange
        String accountNumber = "4200000000000001";
        FreeAccountNumber freeNumber = new FreeAccountNumber(accountNumber, type);
        when(freeAccountNumberRepository.getAndDeleteFirstByAccountType(type.toString()))
                .thenReturn(Optional.of(freeNumber));
        when(freeAccountNumberRepository.existsByAccountType(type)).thenReturn(true);

        //Act
        String result = service.getFreeNumber(type);

        //Assert
        verify(freeAccountNumberRepository, times(1))
                .getAndDeleteFirstByAccountType(type.toString());
        assertEquals(result, accountNumber);
    }

    @Test
    void generateFreeAccountNumbers() {
        //Arrange
        String accountType = type.toString();
        int batchSize = 10;
        AccountNumberSequence accountNumberSequence = new AccountNumberSequence(type, 25);

        when(accountNumberSequenceRepository.incrementCounter(accountType, batchSize)).thenReturn(1);
        when(accountNumberSequenceRepository.findByAccountType(type)).thenReturn(accountNumberSequence);
        when(freeAccountNumberRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        //Act
        service.generateFreeAccountNumbers(type, batchSize);

        //Assert
        verify(accountNumberSequenceRepository, times(1)).createSequenceIfNecessary(accountType);
        verify(accountNumberSequenceRepository, times(1)).incrementCounter(accountType, batchSize);
        verify(freeAccountNumberRepository, times(1)).saveAll(anyList());
    }

    @Test
    void scheduledGenerateFreeNumbers() {
        //Arrange
        int batchSize = 10;
        int currentSequence = 3;
        int updatedBatchSize = batchSize - currentSequence;
        AccountNumberSequence accountNumberSequence = new AccountNumberSequence(type, currentSequence);

        when(freeAccountNumberRepository.countByAccountType(type)).thenReturn(currentSequence);

        when(accountNumberSequenceRepository.incrementCounter(type.toString(),
                updatedBatchSize)).thenReturn(1);
        when(accountNumberSequenceRepository.findByAccountType(type)).thenReturn(accountNumberSequence);
        when(freeAccountNumberRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        //Act
        service.scheduledGenerateFreeNumbers(type, batchSize);

        //Assert
        verify(freeAccountNumberRepository, times(1)).countByAccountType(type);
        verify(accountNumberSequenceRepository,times(1))
                .incrementCounter(type.toString(),updatedBatchSize);
    }
}