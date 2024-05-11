package faang.school.accountservice.repository;

import faang.school.accountservice.model.cashback.MerchantMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantMappingRepository extends JpaRepository<MerchantMapping, Long> {
}
