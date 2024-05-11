package faang.school.accountservice.scheduler;

import faang.school.accountservice.model.cashback.CashbackOperationMapping;
import faang.school.accountservice.model.cashback.CashbackTariff;
import faang.school.accountservice.model.cashback.MerchantMapping;
import faang.school.accountservice.repository.CashbackOperationMappingRepository;
import faang.school.accountservice.repository.CashbackTariffRepository;
import faang.school.accountservice.repository.MerchantMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class CashbackScheduler {

    private final CashbackTariffRepository cashbackTariffRepository;
    private final CashbackOperationMappingRepository cashbackOperationMappingRepository;
    private final MerchantMappingRepository merchantMappingRepository;


    @Scheduled(cron = "0 0 0 1 * ?")
    public void calculateMonthlyCashback() {
        List<CashbackTariff> tariffs = cashbackTariffRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (CashbackTariff tariff : tariffs) {
            executor.submit(() -> {
                List<MerchantMapping> merchantMappings = merchantMappingRepository.findMerchantMappingsByTariffId(tariff.getId());
                List<CashbackOperationMapping> operationMappings = cashbackOperationMappingRepository.findOperationMappingsByTariffId(tariff.getId());

                for (MerchantMapping merchantMapping : merchantMappings) {
                    BigDecimal cashback = calculateCashback(merchantMapping.getCashbackPercentage(), merchantMapping.getAmount());
                    // Здесь можно добавить код для начисления кэшбека на счет
                }

                for (CashbackOperationMapping operationMapping : operationMappings) {
                    BigDecimal cashback = calculateCashback(operationMapping.getCashbackPercentage(), operationMapping.getAmount());
                    // Здесь можно добавить код для начисления кэшбека на счет
                }
            });
        }
        executor.shutdown();
    }

    private BigDecimal calculateCashback(BigDecimal percentage, BigDecimal amount, String identifier) {
        BigDecimal cashback = amount.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        if ("operation".equals(identifier)) {
            // Если есть кэшбек для продавца, сравниваем и выбираем больший
            MerchantMapping merchantMapping = merchantMappingRepository.findByCashbackTariffIdAndMerchantId(tariff.getId(), merchantId);
            if (merchantMapping != null) {
                BigDecimal merchantCashback = calculateCashback(merchantMapping.getCashbackPercentage(), amount);
                if (merchantCashback.compareTo(cashback) > 0) {
                    cashback = merchantCashback;
                }
            }
        } else if ("merchant".equals(identifier)) {
            // Если есть кэшбек для операции, сравниваем и выбираем больший
            CashbackOperationMapping operationMapping = cashbackOperationMappingRepository.findByCashbackTariffIdAndOperationType(tariff.getId(), operationType);
            if (operationMapping != null) {
                BigDecimal operationCashback = calculateCashback(operationMapping.getCashbackPercentage(), amount);
                if (operationCashback.compareTo(cashback) > 0) {
                    cashback = operationCashback;
                }
            }
        }
        return cashback;
    }
}
