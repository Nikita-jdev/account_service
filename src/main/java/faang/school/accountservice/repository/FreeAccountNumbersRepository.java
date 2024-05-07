package faang.school.accountservice.repository;

import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountNumber, FreeAccountId> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            DELETE FROM free_account_numbers
            WHERE type = :type
            AND account_number = {
                SELECT account_number FROM free_account_numbers
                WHERE type = :type LIMIT 1
            }
            RETURNING account_number, type
            """)
            FreeAccountNumber retrieveAndDeleteFirst(String type);
}