package faang.school.accountservice.dto;

import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;

    @NotNull(message = "Должен быть указан ID владельца счета")
    @Positive (message = "Только положительное значение")
    private long ownerId;

    @NotNull(message = "Укажите тип владельца счета")
    private OwnerType ownerType;

    @Size(min = 12, max = 20, message = "Номер счёта должен содержать от 12 до 20 цифр")
    @Pattern(regexp = "\\d{12,20}")
    private String number;

    @NotNull(message = "Тип учетной записи не может быть пустым")
    private AccountType type;

    @NotNull(message = "Укажите валюту счета")
    private Currency currency;

    private AccountStatus status;
}
