@Service
@RequiredArgsConstructor
public class FreeAccountNumbersService {

    private final FreeAccountNumberRepository freeAccountNumberRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    public String generateNewAccountNumber(String accountType) {
        // Попытка получить и удалить первый свободный номер
        FreeAccountNumber freeAccountNumber = freeAccountNumberRepository.getAndDeleteFirstFreeAccountNumber();

        if (freeAccountNumber != null) {
            return freeAccountNumber.getAccountNumber();
        }

        // Свободного номера нет, инкрементируем счетчик и генерируем новый
        accountNumbersSequenceRepository.incrementSequenceIfValueMatches(accountType, 0); // Инкремент с 0
        long nextNumber = accountNumbersSequenceRepository.getCurrentNumber(accountType); // Получить текущее значение счетчика
        String accountNumber = accountType + String.format("%012d", nextNumber); // Форматирование номера

        // Сохранить новый номер в free_account_numbers
        freeAccountNumberRepository.create(nextNumber, accountType);

        return accountNumber;
    }