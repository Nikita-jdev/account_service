package faang.school.accountservice.dto;

import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.enums.Type;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNumber;
    @NotNull
    private Long ownerId;
    @NotNull
    private Type accountType;
    @NotNull
    private Currency currency;
    private Status status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant closedAt;
}
