package faang.school.accountservice.service;

import faang.school.accountservice.repository.AccountSequenceRepository;
import faang.school.accountservice.repository.FreeAccountRepository;
import org.junit.Before;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class FreeAccountNumbersServiceTest {

    @Container
    static PostgreSQLContainer conteiner = new PostgreSQLContainer<>("postgres:latest");
//                    .withDatabaseName("test")
//                    .withUsername("user")
//                    .withPassword("password");

    private FreeAccountNumbersService freeAccountNumbersService;
    private FreeAccountRepository freeAccountRepository;
    private AccountSequenceRepository accountSequenceRepository;

    @Before
    public void setUp() {
        freeAccountNumbersService = new FreeAccountNumbersService(freeAccountRepository, accountSequenceRepository);
    }

    void connectionEstablished() {
        assertThat(conteiner.isCreated()).isTrue();
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
//        assertEquals(batchSize, generatedNumbers.size());
//        long expectedFirstNumber = PATTERN + accountSequenceRepository.incrementCounter(type.name(), 0).getInitialValue();
//        for (int i = 0; i < batchSize; i++) {
//            assertEquals(expectedFirstNumber + i, generatedNumbers.get(i).getId().getAccountNumber());
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