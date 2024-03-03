package faang.school.accountservice.dto;

import faang.school.accountservice.enums.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeAccountNumberDto {

    @NotNull
    private long id;
    @NotNull(message = "Number must be not null")
    @Size(min = 12, max = 20, message = "account number length should be between 12 and 20")
    private String number;
    @NotNull(message = "Account type must not be null")
    private AccountType accountType;
}
