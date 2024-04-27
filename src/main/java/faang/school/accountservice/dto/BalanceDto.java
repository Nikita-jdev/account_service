package faang.school.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDto {

    private long id;

    private long accountId;

    @NotNull(message = "authorizationBalance can't be null")
    private BigDecimal authorizationBalance;

    @NotNull(message = "actualBalance can't be null")
    private BigDecimal actualBalance;
}
