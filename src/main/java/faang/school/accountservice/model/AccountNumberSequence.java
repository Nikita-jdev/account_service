package faang.school.accountservice.model;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_numbers_sequence")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountNumberSequence {

    @Id
    @Column(name = "account_type", length = 32)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "counter", nullable = false)
    private long counter;

}