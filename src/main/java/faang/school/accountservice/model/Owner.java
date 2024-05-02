package faang.school.accountservice.model;

import faang.school.accountservice.enums.OwnerType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_id", nullable = false)
    private long accountId;

    @Column(name = "owner_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OwnerType ownerType;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Account> accounts;
}
