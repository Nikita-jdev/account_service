package faang.school.accountservice.model.account.numbers;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "free_account_numbers")
@NoArgsConstructor
@AllArgsConstructor
public class FreeAccountNumber {

    @EmbeddedId
    private FreeAccountId id;

    @Column(name = "account_type", nullable = false, unique = true)
    private String accountType;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    public FreeAccountNumber(String accountType, String accountNumber) {
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }
}