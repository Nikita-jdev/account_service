package faang.school.accountservice.repository;

import faang.school.accountservice.entity.FreeAccountNumberSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FreeAccountNumbersSeqRepository extends JpaRepository<FreeAccountNumberSequence, Long> {

    @Query(nativeQuery = true, value = """
            UPDATE free_account_numbers_sequence
            SET count = count + :batchSize
            WHERE account_type = :type
            """)
    @Modifying
    int incrementCounter(String type, int batchSize);

    @Query(nativeQuery = true, value = """
            SELECT count FROM free_account_numbers_sequence
            WHERE account_type = :type
            """)
    Long getSeqByType(String type);
}