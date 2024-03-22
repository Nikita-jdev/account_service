package faang.school.accountservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "account_number_sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSeq {

    @Id
    @Column(name = "type", nullable = false, length = 32)
    private AccountType type;

    @Column(name = "counter", nullable = false)
    private long counter;

    @Transient
    private long initialValue;
}
