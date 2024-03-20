package faang.school.accountservice.repository;

import faang.school.accountservice.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<Request, UUID> {
    Optional<Request> findByLockValue(String lockValue);
}
