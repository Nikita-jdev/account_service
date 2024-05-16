package faang.school.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BalanceAuditDto {
    private long id;
    @NotNull
    private Long accountId;
    private long authorizedBalance;
    private long actualBalance;
    private long balanceVersion;

}