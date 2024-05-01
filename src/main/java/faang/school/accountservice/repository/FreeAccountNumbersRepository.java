package faang.school.accountservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeAccountNumberRepository implements JpaRepository<Number,Long>{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public FreeAccountNumber createFreeAccountNumber() {
        Long nextNumber = entityManager.createNativeQuery("SELECT nextval('free_account_numbers_seq')")
                .getSingleResult() instanceof Long ? (Long) entityManager.createNativeQuery("SELECT nextval('free_account_numbers_seq')")
                .getSingleResult() : null;
        if (nextNumber != null) {
            FreeAccountNumber accountNumber = new FreeAccountNumber(nextNumber);
            entityManager.persist(accountNumber);
            return accountNumber;
        }
        return null;
    }

    @Override
    @Transactional
    public FreeAccountNumber getAndDeleteFirstFreeAccountNumber() {
        Query query = entityManager.createNativeQuery("DELETE FROM free_account_numbers RETURNING number LIMIT 1");
        Object result = query.getSingleResult();
        return result instanceof Long ? new FreeAccountNumber((Long) result) : null;
    }
}