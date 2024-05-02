package faang.school.accountservice.repository;

import faang.school.accountservice.model.account.numbers.AccountNumbersSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountNumbersSequenceRepository extends JpaRepository<AccountNumbersSequence, Long> {

    @Transactional
    void createSequenceForAccountType(String accountType);

    @Transactional
    boolean incrementSequenceIfValueMatches(String accountType, long expectedValue);
}