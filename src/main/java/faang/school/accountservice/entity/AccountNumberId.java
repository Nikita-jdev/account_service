package faang.school.accountservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class AccountNumberId {
    @Column(name = "account_number", nullable = false, unique = false)
    private long accountNumber;
    @Column(name = "type", nullable = false, unique = false, length = 32)
    private String type;
}
