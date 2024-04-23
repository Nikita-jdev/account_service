package faang.school.accountservice.dto.account;

import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.model.account.AccountStatus;
import faang.school.accountservice.model.account.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private long id;
    @NotBlank(message = "Account number can't be blank")
    private String number;
    private long ownerId;
    @NotNull(message = "Account type can't be empty")
    private AccountType accountType;
    @NotNull(message = "Account currency can't be empty")
    private Currency currency;
    private AccountStatus accountStatus;
}
