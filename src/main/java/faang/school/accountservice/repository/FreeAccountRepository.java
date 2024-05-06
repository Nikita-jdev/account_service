package faang.school.accountservice.repository;

import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FreeAccountRepository extends JpaRepository<FreeAccountNumber, FreeAccountId> {

    @Query(nativeQuery = true,
            value = """
                    DELETE FROM free_account_numbers fan
                    WHERE fan type  = :type AND fan.account_number = {
                    SELECT account_number
                    FROM free_account_numbers
                    WHERE type = :type
                    LIMIT 1
                    }
                    RETURNING fan.account_number, fan_type
                    """)
    FreeAccountNumber retrieveFirst(String type);

    @Transactional
    @Query(nativeQuery = true,
            value = """
                        DELETE FROM free_account_numbers fan
                        WHERE fan.type = :type
                        RETURNING fan.account_number, fan.type
                    """)
    FreeAccountNumber findAndDeleteFirstFreeAccountNumber(String type);
}