package faang.school.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {
    private Long id;
    private String accountNumber;
    private BigDecimal authorizationBalance;
    private BigDecimal clearingBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
