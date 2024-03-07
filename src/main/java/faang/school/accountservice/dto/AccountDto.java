package faang.school.accountservice.dto;

import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
