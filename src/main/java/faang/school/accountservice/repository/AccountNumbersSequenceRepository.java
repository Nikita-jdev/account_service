
@Repository
public interface AccountNumbersSequenceRepository extends JpaRepository<AccountNumbersSequence, String> {

    @Transactional
    void createSequenceForAccountType(String accountType);

    @Transactional
    boolean incrementSequenceIfValueMatches(String accountType, long expectedValue);
}