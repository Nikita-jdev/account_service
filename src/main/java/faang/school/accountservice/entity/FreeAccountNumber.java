package faang.school.accountservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "free_account_numbers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeAccountNumber {

    @EmbeddedId
    private FreeAccountId id;
}
