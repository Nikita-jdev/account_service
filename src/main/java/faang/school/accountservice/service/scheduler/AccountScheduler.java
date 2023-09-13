package faang.school.accountservice.service.scheduler;

import faang.school.accountservice.model.AccountType;
import faang.school.accountservice.service.FreeAccountNumbersService;
import faang.school.accountservice.service.SavingsAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountScheduler {
    private final FreeAccountNumbersService freeAccountNumbersService;
    private final SavingsAccountService savingsAccountService;
    @Value("${account.generate.count}")
    private long accountCount;
    @Value("${account.calculate.batchSize}")
    private int batchSize;

    @Scheduled(cron = "${account.generate.cron}")
    private void generateSavingAccountNumbers(){
        for (int i = 0; i < accountCount; i++) {
            freeAccountNumbersService.generateAccountNumber(AccountType.SAVINGS_ACCOUNT);
        }
    }

    @Scheduled(cron = "${account.calculate.cron}")
    private void calculateAndApplyInterest() {
            savingsAccountService.updateInterest(batchSize);
    }
}
