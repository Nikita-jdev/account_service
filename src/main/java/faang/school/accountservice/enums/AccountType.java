package faang.school.accountservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
    PAYMENT(1100),
    CREDIT(2100),
    INVESTMENT(3100),
    CURRENCY(4100),
    DEPOSIT(5100),
    SPECIAL(9999);

    private int prefix;
}
