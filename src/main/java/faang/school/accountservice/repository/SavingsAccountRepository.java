package faang.school.accountservice.repository;

import faang.school.accountservice.entity.account.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {
    SavingsAccount findByAccount_Id(Long accountId);

    @Query("SELECT sa FROM SavingsAccount sa WHERE sa.lastUpdateCalculationAt IS NULL OR sa.lastUpdateCalculationAt < :cutoffTime")
    List<SavingsAccount> findByLastUpdateTime(@Param("cutoffTime") LocalDateTime cutoffTime);
}