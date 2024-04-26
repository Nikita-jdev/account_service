package faang.school.accountservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FreeAccountNumbersRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public String createFreeAccountNumber(String accountType) {
        // Инкрементировать счетчик
        int nextAccountNumber = jdbcTemplate.update("UPDATE account_numbers_sequence SET current_value = current_value + 1 WHERE account_type = ?", accountType);

        // Получить новый номер счетчика
        int newAccountNumber = jdbcTemplate.queryForObject("SELECT current_value FROM account_numbers_sequence WHERE account_type = ?", Integer.class, accountType);

        // Сформировать номер счета
        String formattedAccountNumber = formatAccountNumber(accountType, newAccountNumber);

        // Добавить номер счета в free_account_numbers
        jdbcTemplate.update("INSERT INTO free_account_numbers (account_type, account_number) VALUES (?, ?)", accountType, formattedAccountNumber);

        return formattedAccountNumber;
    }

    @Transactional
    public String findAndDeleteFreeAccountNumber(String accountType) {
        return jdbcTemplate.queryForObject("DELETE FROM free_account_numbers WHERE account_type = ? ORDER BY id LIMIT 1 RETURNING account_number", String.class, accountType);
    }

    private String formatAccountNumber(String accountType, int accountNumber) {
        return null;
    }

}
