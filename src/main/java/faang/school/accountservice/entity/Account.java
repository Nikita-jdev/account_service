package faang.school.accountservice.entity;

import faang.school.accountservice.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "account_owner")
    private Owner accountOwner;

    @Column(name = "account_type", length = 32)
    @Enumerated(EnumType.STRING)
    private String accountType;

    @Column(name = "currency", length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "status", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private String status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "version", nullable = false)
    @Version
    private long version;
}
