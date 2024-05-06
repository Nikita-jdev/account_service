package faang.school.accountservice.service;

import faang.school.accountservice.model.account.numbers.AccountType;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import faang.school.accountservice.repository.AccountSequenceRepository;
import faang.school.accountservice.repository.FreeAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FreeAccountNumbersServiceTest {

    private FreeAccountNumbersService freeAccountNumbersService;
    private FreeAccountRepository freeAccountRepository;
    private PostgreSQLContainer<?> postgresContainer;
    private AccountSequenceRepository accountSequenceRepository;

    @BeforeEach
    void setUp() {
        postgresContainer = new PostgreSQLContainer<>();
        postgresContainer.start();
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @AfterEach
    void tearDown() {
        postgresContainer.stop();
    }

    @Test
    void generateAccountNumbers() {
        AccountType type = AccountType.CREDIT;
        int batchSize = 2;
        final long PATTERN = 4200_0000_0000_0000L;

        freeAccountNumbersService.generateAccountNumbers(type, batchSize);

        List<FreeAccountNumber> generatedNumbers = freeAccountRepository.findAll();
        assertEquals(batchSize, generatedNumbers.size());
        long expectedFirstNumber = PATTERN + accountSequenceRepository.incrementCounter(type.name(), 0).getInitialValue();
        for (int i = 0; i < batchSize; i++) {
            assertEquals(expectedFirstNumber + i, generatedNumbers.get(i).getId().getAccountNumber());
        }
    }

//    @Test
//    void retrieveAccountNumber() {
//        AccountType type = AccountType.CREDIT;
//        long initialCounter = accountSequenceRepository.incrementCounter(type.name(), 0).getCounter();
//
//        MockConsumer<FreeAccountNumber> mockConsumer = new MockConsumer<>();
//        freeAccountNumbersService.retrieveAccountNumber(type, mockConsumer);
//
//        FreeAccountNumber retrievedNumber = (FreeAccountNumber) mockConsumer.recordedCalls.get(0).getArgument(0);
//        assertNotNull(retrievedNumber);
//        assertEquals(type, retrievedNumber.getId().getType());
//        assertEquals(initialCounter, retrievedNumber.getId().getAccountNumber());
//    }
}