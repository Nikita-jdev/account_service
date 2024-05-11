package faang.school.accountservice.repository;

import faang.school.accountservice.model.cashback.CashbackOperationMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashbackOperationMappingRepository extends JpaRepository<CashbackOperationMapping, Long> {
}