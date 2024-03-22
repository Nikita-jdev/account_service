package faang.school.accountservice.scheduler;

import faang.school.accountservice.entity.AccountType;
import faang.school.accountservice.service.FreeAccountNumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountNumberScheduler {

    @Value("${spring.datasource.debitPattern}")
    private long debitPattern;
    @Value("${spring.datasource.savingsPattern}")
    private long savingsPattern;
    @Value("${spring.datasource.batchSize}")
    private int batchSize;
    private final FreeAccountNumbersService freeAccountNumbersService;

    @Scheduled(cron = "0 0 0 * * *")
    public void generateDebit() {
        freeAccountNumbersService.generateAccountNumbers(AccountType.DEBIT, batchSize, debitPattern);
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void generateSavings() {
        freeAccountNumbersService.generateAccountNumbers(AccountType.SAVINGS, batchSize, savingsPattern);
    }

}
