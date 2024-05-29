package faang.school.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accountNumber;

    @NotNull(message = "Owner ID must not be null")
    private long ownerId;

    @NotNull(message = "Type account must not be null")
    private AccountType type;

    @NotNull(message = "Type currency must not be null")
    private Currency currency;

    @NotNull(message = "Status must not be null")
    private Status status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant closedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long version;

}