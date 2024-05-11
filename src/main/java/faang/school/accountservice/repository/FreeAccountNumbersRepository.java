package faang.school.accountservice.repository;

import faang.school.accountservice.entity.AccountNumber;
import faang.school.accountservice.entity.AccountNumberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<AccountNumber, AccountNumberId> {
    @Query(nativeQuery = true, value = """
                             DELETE FROM free_account_numbers
                             WHERE type = :type and account_number = (
                                SELECT account_number from free_account_numbers
                                WHERE type = :type
                                LIMIT 1
                             )
                             RETURNING account_number, type
                            """)
    @Modifying
    Optional<AccountNumber> getAccountNumber(String type);
}
