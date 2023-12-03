package faang.school.accountservice.entity.account.numbers;

import faang.school.accountservice.entity.account.AccountType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account_number_sequence")
public class AccountNumberSequence {
    @Id
    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 32)
    private AccountType accountType;

    @Column(name = "current_count", nullable = false, length = 32)
    private Long currentCount;
}