package faang.school.accountservice.scheduler;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.service.FreeAccountNumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FreeAccountNumberScheduler {

    @Value("${scheduler.account_number.batchSize}")
    private int batchSize;
    private final FreeAccountNumbersService service;

    @Scheduled(cron = "${scheduler.generateNumbersCron}")
    public void generatePaymentAccountNumber() {
        service.generateNumber(batchSize, AccountType.PAYMENT);
    }

    @Scheduled(cron = "${scheduler.generateNumbersCron}")
    public void generateCreditAccountNumber() {
        service.generateNumber(batchSize, AccountType.CREDIT);
    }

    @Scheduled(cron = "${scheduler.generateNumbersCron}")
    public void generateInvestmentAccountNumber() {
        service.generateNumber(batchSize, AccountType.INVESTMENT);
    }

    @Scheduled(cron = "${scheduler.generateNumbersCron}")
    public void generateCurrencyAccountNumber() {
        service.generateNumber(batchSize, AccountType.CURRENCY);
    }

    @Scheduled(cron = "${scheduler.generateNumbersCron}")
    public void generateDepositAccountNumber() {
        service.generateNumber(batchSize, AccountType.DEPOSIT);
    }

    @Scheduled(cron = "${scheduler.generateNumbersCron}")
    public void generateSpecialAccountNumber() {
        service.generateNumber(batchSize, AccountType.SPECIAL);
    }
}