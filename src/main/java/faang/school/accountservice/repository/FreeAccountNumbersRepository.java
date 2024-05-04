package faang.school.accountservice.repository;

import faang.school.accountservice.model.account.numbers.FreeAccountId;
import faang.school.accountservice.model.account.numbers.FreeAccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FreeAccountNumbersRepository extends JpaRepository<FreeAccountId, Long> {

    @Transactional
    FreeAccountNumber findAndDeleteFirstFreeAccountNumberByAccountType(String accountType);
}