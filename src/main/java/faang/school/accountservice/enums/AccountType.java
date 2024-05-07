package faang.school.accountservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
    //TODO: значения в скобках тут надо писать или вынести в конфиг?
    // и что лучше писать цифры или текстом тип счета, например - "кредитный счет",
    // "Иностранный счет", нам в будущем нужно будет записи в скобках как-то использовать?
    // я думаю, если только типа для конкатенации строк и вывода какого-то ответа или все это лишнее?
    PHYSICAL_PERSONS_ACCOUNT("4400"),
    LEGAL_ENTITIES_ACCOUNT("4500"),
    PAYMENT_ACCOUNT("5100"),
    DEPOSIT_ACCOUNT("5200"),
    SAVINGS_ACCOUNT("5300"),
    CREDIT_ACCOUNT("5400"),
    CURRENT_ACCOUNT("5500"),
    CURRENCY_ACCOUNT("6100");

    private final String accountType;
}