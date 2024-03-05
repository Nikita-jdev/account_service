package faang.school.accountservice.dto;

import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.enums.Type;

import java.time.LocalDateTime;

public class AccountDto {
    private Long id;
    private String accountNumber;
    private Long accountOwner;
    private Type accountType;
    private Currency currency;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;

}
