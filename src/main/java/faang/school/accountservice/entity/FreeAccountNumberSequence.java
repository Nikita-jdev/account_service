package faang.school.accountservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "free_account_numbers_sequence")
public class FreeAccountNumberSequence {
    @Id
    @Column(name = "account_type", nullable = false)
    private String type;

    @Column(name = "count", nullable = false)
    private long count;
}
