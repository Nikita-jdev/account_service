package faang.school.accountservice.repository;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository <FreeAccountNumber, Long>{

    @Query(nativeQuery = true, value = """
        DELETE FROM free_account_numbers
        WHERE number = (
            SELECT number
            FROM free_account_numbers
            WHERE account_type = :type
            ORDER BY random()
            LIMIT 1)
        RETURNING number
        """)
    String findAndDeleteFreeAccountNumberByAccountType(@Param("type") AccountType accountType);
}
