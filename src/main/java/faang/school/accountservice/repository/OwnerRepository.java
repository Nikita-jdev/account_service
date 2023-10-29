package faang.school.accountservice.repository;

import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.entity.Tariff;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("SELECT u FROM User u WHERE u.tariff = :tariff")
    List<Owner> findByTariff(@Param("tariff") Tariff tariff);
}