package faang.school.accountservice.repository;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAccountNumberRepository extends JpaRepository<FreeAccountNumber, String> {

    @Query(nativeQuery = true, value = """
            DELETE FROM free_account_numbers WHERE account_number =
            (SELECT account_number FROM free_account_numbers 
            WHERE account_type = :accountType ORDER BY account_number LIMIT 1)
            RETURNING *
            """)
    Optional<FreeAccountNumber> getAndDeleteFirstByAccountType(String accountType);

    boolean existsByAccountType(AccountType accountType);

    int countByAccountType(AccountType accountType);
}