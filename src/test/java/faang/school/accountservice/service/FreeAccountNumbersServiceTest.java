package faang.school.accountservice.service;

import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import faang.school.accountservice.repository.AccountNumbersSequenceRepository;
import faang.school.accountservice.repository.FreeAccountNumbersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static faang.school.accountservice.model.account.numbers.AccountType.CREDIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FreeAccountNumbersServiceTest {

    @Autowired
    private FreeAccountNumbersRepository freeAccountNumbersRepository;
    @Autowired
    private AccountNumbersSequenceRepository accountNumbersSequenceRepository;
    @Autowired(required = true)
    private FreeAccountNumbersService freeAccountNumbersService;

    @Container
    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

    @Test
    void connectionEstablished() {
        assertThat(container.isCreated()).isTrue();
        assertThat(container.isRunning()).isTrue();
    }

    @BeforeEach
    public void setUp() {
        String type = "CREDIT";
        int batchSize = 2;

    }

    @Test
    public void retrieveFirst_shouldReturnFirstFreeAccountNumber() {
        String type = "SAVINGS"; // Test with different type
        int batchSize = 1;

        // Increment counter to simulate available numbers
        accountNumbersSequenceRepository.incrementCounter(type, batchSize);

        // Create a test FreeAccountNumber object
        FreeAccountNumber expectedAccount = new FreeAccountNumber(new FreeAccountId(CREDIT, 1000000000000001L));

        // Save the test data
        freeAccountNumbersRepository.save(expectedAccount);

        // Retrieve the first account number
        FreeAccountNumber retrievedAccount = freeAccountNumbersRepository.retrieveFirst(type);

        // Assertions
        assertNotNull(retrievedAccount);
        Assertions.assertEquals(expectedAccount.getId().getType(), retrievedAccount.getId().getType());
        Assertions.assertEquals(expectedAccount.getId().getAccountNumber(), retrievedAccount.getId().getAccountNumber());
    }
//    @Test
//    void generateAccountNumbers() {
//        AccountType type = AccountType.CREDIT;
//        int batchSize = 2;
//        final long PATTERN = 4200_0000_0000_0000L;
//
//        freeAccountNumbersService.generateAccountNumbers(type, batchSize);
//
//        List<FreeAccountNumber> generatedNumbers = freeAccountRepository.findAll();
//        Assertions.assertEquals(batchSize, generatedNumbers.size());
//        long expectedFirstNumber = PATTERN + accountSequenceRepository.incrementCounter(type.name(), 0).getInitialValue();
//        for (int i = 0; i < batchSize; i++) {
//            Assertions.assertEquals(expectedFirstNumber + i, generatedNumbers.get(i).getId().getAccountNumber());
//        }
//    }
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