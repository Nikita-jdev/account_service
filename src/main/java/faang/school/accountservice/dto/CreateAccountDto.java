package faang.school.accountservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateAccountDto {
    @NotNull
    private Long ownerId;
    @NotNull
    private String accountType;
    @NotNull
    private String currency;
}
