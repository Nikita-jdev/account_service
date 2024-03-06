package faang.school.accountservice.repository;

import faang.school.accountservice.entity.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository <FreeAccountNumber, Long>{

    @Query(nativeQuery = true, value = """
        DELETE FROM free_account_numbers
        WHERE number AND account_type = (
            SELECT number
            FROM free_account_numbers
            WHERE account_type = :type
            ORDER BY random()
            LIMIT 1)
        RETURNING number, account_type
        """)
    @Modifying
    FreeAccountNumber findFirstAndDeleteNumber(String type);
}
