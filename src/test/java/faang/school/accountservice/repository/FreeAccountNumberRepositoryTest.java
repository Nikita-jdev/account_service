package faang.school.accountservice.repository;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.FreeAccountNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@TestPropertySource(properties = "spring.test.database.replace=none")
class FreeAccountNumberRepositoryTest {

    @Autowired
    private FreeAccountNumberRepository freeAccountNumberRepository;
    private FreeAccountNumber number;
    private AccountType type;

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

    @BeforeEach
    void SetUp() {
        type = AccountType.DEBIT;
        number = new FreeAccountNumber("4200000000000001", type);
        freeAccountNumberRepository.save(number);
    }


    @Test
    void testGetAndDeleteFirstByAccountType() {
        //Arrange
        String accountType = type.toString();

        //Act
        FreeAccountNumber result = freeAccountNumberRepository.getAndDeleteFirstByAccountType(accountType).orElse(null);

        //Assert
        assertNotNull(result);
        assertEquals(result.getAccountNumber(), number.getAccountNumber());
        assertEquals(result.getAccountType(), number.getAccountType());
        assertTrue(freeAccountNumberRepository.findAll().isEmpty());
    }

    @Test
    void testExistsByAccountType_whenExistRecord_thenReturnTrue() {
        //Act
        boolean result = freeAccountNumberRepository.existsByAccountType(type);

        //Assert
        assertTrue(result);
    }
    @Test
    void testExistsByAccountType_whenNotExistRecord_thenReturnFalse() {
        //Act
        boolean result = freeAccountNumberRepository.existsByAccountType(AccountType.SAVINGS);

        //Assert
        assertFalse(result);
    }

    @Test
    void countByAccountType() {
        //Arrange
        List<FreeAccountNumber> numbers = List.of(
                new FreeAccountNumber("4200000000000001", type),
                new FreeAccountNumber("4200000000000002", type),
                new FreeAccountNumber("4200000000000003", type)
        );
        freeAccountNumberRepository.saveAll(numbers);

        //Act
        int result = freeAccountNumberRepository.countByAccountType(type);

        //Assert
        assertEquals(result, numbers.size());
    }
}