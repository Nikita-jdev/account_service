package faang.school.accountservice.repository;

import faang.school.accountservice.model.cashback.CashbackOperationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CashbackOperationMappingRepository extends JpaRepository<CashbackOperationMapping, Long> {

    @Query("SELECT o FROM CashbackOperationMapping o WHERE o.cashbackTariff.id = :tariffId")
    List<CashbackOperationMapping> findOperationMappingsByTariffId(@Param("tariffId") Long tariffId);
}