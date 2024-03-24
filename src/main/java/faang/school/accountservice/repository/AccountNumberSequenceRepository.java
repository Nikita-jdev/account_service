package faang.school.accountservice.repository;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.model.AccountNumberSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberSequenceRepository extends JpaRepository<AccountNumberSequence, String> {

    @Query(nativeQuery = true, value = """
            INSERT INTO account_numbers_sequence (account_type, counter)
            VALUES (:accountType,0)
            ON CONFLICT DO NOTHING 
            """)
    @Modifying
    void createSequenceIfNecessary(String accountType);

    @Query(nativeQuery = true, value = """
            UPDATE account_numbers_sequence SET counter = counter + :batchSize
            WHERE account_type = :accountType
                        """)
    @Modifying(clearAutomatically = true)
    int incrementCounter(String accountType, int batchSize);

    AccountNumberSequence findByAccountType(AccountType accountType);

}