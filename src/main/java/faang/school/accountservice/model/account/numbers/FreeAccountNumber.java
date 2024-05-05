package faang.school.accountservice.model.account.numbers;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "free_account_numbers")
@Data
@AllArgsConstructor
public class FreeAccountNumber {

    @EmbeddedId
    private FreeAccountId id;

}