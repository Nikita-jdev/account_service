package faang.school.accountservice.dto;

import faang.school.accountservice.enums.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RedisPaymentDto(
        @NotNull
        Long userId,
        @NotNull
        long senderBalanceNumber,
        @NotNull
        long getterBalanceNumber,
        @Min(1)
        BigDecimal amount,
        @NotNull
        Currency currency,
        @NotNull
        PaymentStatus status
) {
}
