package faang.school.accountservice.repository;

import faang.school.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByNumber(String number);

    Optional<Account> findByNumber(String number);

    Optional<Account> findByOwnerId(Long ownerId);

}