package faang.school.accountservice.scheduler;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.service.FreeAccountNumbersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountNumbersScheduler {
    private final FreeAccountNumbersService freeAccountNumbersService;

    @Value("${scheduler.number-generation.size_batch}")
    private int batchSize;

    @Scheduled(cron = "${scheduler.number-generation.cron}")
    public void generateAllNumbers() {
        log.info("Start generation account numbers all types");
        AccountType[] values = AccountType.values();
        for (int i = 0; i < values.length; i++) {
            freeAccountNumbersService.scheduledGenerateFreeNumbers(values[i], batchSize);
        }
    }
}