package faang.school.accountservice.repository;

import faang.school.accountservice.entity.AccountNumber;
import faang.school.accountservice.entity.AccountNumberId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
public class FreeAccountNumberRepositoryTest {
    @Autowired
    private FreeAccountNumbersRepository freeAccountNumbersRepository;

    @Container
    public static final PostgreSQLContainer postgresContainer =
            new PostgreSQLContainer<>("postgres:latest");
    @Test
    public void testSaveAccountNumber(){
        AccountNumber accountNumber = new AccountNumber(new AccountNumberId(4200_0000_0000_0000L, "debit"));
        freeAccountNumbersRepository.save(accountNumber);
    }
}
