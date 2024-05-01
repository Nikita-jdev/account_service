package faang.school.accountservice.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountNumbersSequenceRepository extends JpaRepository<AccountNumbersSequence, String> {

    @Transactional
    void createSequenceForAccountType(String accountType);

    @Transactional
    boolean incrementSequenceIfValueMatches(String accountType, long expectedValue);
}