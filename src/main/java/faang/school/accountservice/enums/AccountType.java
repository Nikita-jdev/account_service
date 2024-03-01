package faang.school.accountservice.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    DEBIT("4200"),
    SAVINGS("5236"),
    OTHER_CURRENCY("6200");

    private String prefixNumber;

    AccountType(String prefixNumber) {
        this.prefixNumber = prefixNumber;
    }
}