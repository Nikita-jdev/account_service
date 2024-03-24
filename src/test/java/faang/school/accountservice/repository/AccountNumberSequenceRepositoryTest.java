package faang.school.accountservice.repository;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.AccountNumberSequence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@TestPropertySource(properties = "spring.test.database.replace=none")
class AccountNumberSequenceRepositoryTest {

    @Autowired
    private AccountNumberSequenceRepository accountNumberSequenceRepository;
    private AccountType type = AccountType.DEBIT;

    @Container
    public static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:13.3")
                    .withInitScript("init.sql");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        POSTGRESQL_CONTAINER.start();
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    @Test
    void testCreateSequenceIfNecessary_whenTableIsEmpty_thenCreate() {
        //Arrange
        int initialSequence = 0;

        //Act
        accountNumberSequenceRepository.createSequenceIfNecessary(type.toString());
        AccountNumberSequence sequence = accountNumberSequenceRepository.findByAccountType(type);

        //Arrange
        assertEquals(initialSequence, sequence.getCounter());
        assertEquals(accountNumberSequenceRepository.count(), 1);
    }

    @Test
    void testIncrementCounter_whenExistRecordByAccountType_thenIncrementCounter() {
        //Arrange
        String accountType = type.toString();
        int batchSize = 10;
        accountNumberSequenceRepository.createSequenceIfNecessary(accountType);
        long currentSequence = accountNumberSequenceRepository.findByAccountType(type).getCounter();

        //Act
        int result = accountNumberSequenceRepository.incrementCounter(accountType, batchSize);
        long newSequence = accountNumberSequenceRepository.findByAccountType(type).getCounter();

        //Arrange
        int modifyingFields = 1;
        long correctNewSequence = batchSize + currentSequence;
        assertEquals(result, modifyingFields);
        assertEquals(newSequence, correctNewSequence);
    }

    @Test
    void testFindByAccountType_whenExistRecordByAccountType_thenReturnRecord() {
        //Arrange
        AccountNumberSequence accountNumberSequence = new AccountNumberSequence(type, 1);
        accountNumberSequenceRepository.save(accountNumberSequence);

        //Act
        AccountNumberSequence result = accountNumberSequenceRepository.findByAccountType(type);

        //Arrange
        assertEquals(result,accountNumberSequence);
    }
}