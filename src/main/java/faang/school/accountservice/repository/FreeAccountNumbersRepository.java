package faang.school.accountservice.repository;

import faang.school.accountservice.entity.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountNumber, Long> {

    @Query(nativeQuery = true, value = """
            DELETE FROM free_account_numbers nums
            WHERE nums.account_type = :type AND nums.number = (
                SELECT number
                FROM free_account_numbers
                WHERE account_type = :type
                ORDER BY number
                LIMIT 1)
            RETURNING nums.number
            """)
    String findFirstAndDeleteNumber(String type);
}
