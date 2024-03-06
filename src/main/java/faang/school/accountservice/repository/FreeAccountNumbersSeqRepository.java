package faang.school.accountservice.repository;

import faang.school.accountservice.entity.FreeAccountNumberSequence;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FreeAccountNumbersSeqRepository extends JpaRepository<FreeAccountNumberSequence, Long> {
    @Query(nativeQuery = true, value = """
            UPDATE free_account_numbers_sequence s SET
            count = s.count + 1
            WHERE s.account_type =: type AND s.count =: count
            RETURNING s.account_type, s.count
            """)
    FreeAccountNumberSequence getFreeAccountNumberSequence(int count, String type);
}
