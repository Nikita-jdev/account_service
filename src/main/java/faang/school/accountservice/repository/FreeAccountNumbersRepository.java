package faang.school.accountservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FreeAccountNumberRepositoryImpl implements CrudRepository{

    @PersistenceContext
    private EntityManager entityManager;

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