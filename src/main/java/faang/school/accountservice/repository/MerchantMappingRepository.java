package faang.school.accountservice.repository;

import faang.school.accountservice.model.cashback.MerchantMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantMappingRepository extends JpaRepository<MerchantMapping, Long> {
    @Query("SELECT m FROM MerchantMapping m WHERE m.merchantId = :merchantId AND m.category = :merchantCategory")
    MerchantMapping findByMerchantIdAndCategory(@Param("merchantId") String merchantId, @Param("merchantCategory") String merchantCategory);
}
