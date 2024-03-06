package faang.school.accountservice.entity;

import faang.school.accountservice.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeAccountNumberId implements Serializable {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "number", nullable = false, unique = true, length = 20)
    private String number;
}
