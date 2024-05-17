package faang.school.accountservice.repository;

import faang.school.accountservice.entity.AccountSeq;
import faang.school.accountservice.entity.AccountType;
import faang.school.accountservice.entity.FreeAccountId;
import faang.school.accountservice.entity.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountNumber, FreeAccountId>{

    @Query(nativeQuery = true, value = """
            DELETE FROM free_account_numbers fan
            WHERE fan.type=:type AND fan.account_number=(
            SELECT account_number FROM 
            free_account_numbers WHERE type = :type
            LIMIT 1
            )
            RETURNING fan.account_number, fan.type
            """)
    @Modifying
    Optional<FreeAccountNumber> retrieveFirst(String type);


}
