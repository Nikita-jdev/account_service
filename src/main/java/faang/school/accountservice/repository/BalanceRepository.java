package faang.school.accountservice.repository;

import faang.school.accountservice.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByAccountNumber(String account);
}
