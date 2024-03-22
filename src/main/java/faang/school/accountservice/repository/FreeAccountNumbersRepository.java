package faang.school.accountservice.repository;

import faang.school.accountservice.entity.FreeAccountNumber;
import faang.school.accountservice.entity.FreeAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountNumber, FreeAccountId> {

    @Query(nativeQuery = true, value = """
            DELETE FROM free_account_numbers free
            WHERE free.type = :type AND free.account_number = (
            SELECT account_number
            FROM free_account_numbers
            WHERE free.type = :type
            LIMIT 1
            )
            RETURNING free.account_number, free.type
            """)
    @Modifying
    FreeAccountNumber retrieveFirst(String type);
}
