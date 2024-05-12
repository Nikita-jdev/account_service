package faang.school.accountservice.scheduler;

import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.model.balance.Balance;
import faang.school.accountservice.model.cashback.CashbackOperationMapping;
import faang.school.accountservice.model.cashback.CashbackTariff;
import faang.school.accountservice.model.cashback.MerchantMapping;
import faang.school.accountservice.model.cashback.Transaction;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.CashbackOperationMappingRepository;
import faang.school.accountservice.repository.CashbackTariffRepository;
import faang.school.accountservice.repository.MerchantMappingRepository;
import faang.school.accountservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CashbackScheduler {

    private final CashbackTariffRepository cashbackTariffRepository;
    private final CashbackOperationMappingRepository cashbackOperationMappingRepository;
    private final MerchantMappingRepository merchantMappingRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void calculateMonthlyCashback() {
        List<Account> accounts = accountRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (Account account : accounts) {
            executor.submit(() -> {
                Balance totalBalance = account.getBalance();
                BigDecimal totalCashback = BigDecimal.ZERO;

                List<Transaction> transactions = transactionRepository.findByAccountId(account.getId());
                for (Transaction transaction : transactions) {
                    BigDecimal transactionAmount = transaction.getAmount();
                    String merchantId = transaction.getMerchantId();
                    BigDecimal cashbackPercentage = calculateCashback(transactionAmount, transaction.getOperationType(), transaction.getMerchantCategory(), merchantId);
                    BigDecimal transactionCashback = transactionAmount.multiply(cashbackPercentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    totalCashback = totalCashback.add(transactionCashback);
                }
                totalBalance.add(totalCashback);
                accountRepository.save(account);
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private BigDecimal calculateCashback(BigDecimal amount, String operationType, String merchantCategory, String merchantId) {
        BigDecimal cashback = BigDecimal.ZERO;
        CashbackOperationMapping operationMapping = cashbackOperationMappingRepository.findByOperationTypeAndCategory(operationType, merchantCategory);
        if (operationMapping != null) {
            CashbackTariff tariff = operationMapping.getCashbackTariff();
            if (tariff != null) {
                cashback = cashback.max(operationMapping.getCashbackPercentage());
            }
        }
        MerchantMapping merchantMapping = merchantMappingRepository.findByMerchantIdAndCategory(merchantId, merchantCategory);
        if (merchantMapping != null) {
            CashbackTariff tariff = merchantMapping.getCashbackTariff();
            if (tariff != null) {
                cashback = cashback.max(merchantMapping.getCashbackPercentage());
            }
        }
        return amount.multiply(cashback).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
