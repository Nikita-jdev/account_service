package faang.school.accountservice.entity;

import faang.school.accountservice.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", length = 20, nullable = false, unique = true)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "status_details", length = 128)
    private String statusDetails;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant updatedAt;

    @Column(name = "closed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant closingDate;

    @Version
    @Column(name = "version")
    private long version;
}