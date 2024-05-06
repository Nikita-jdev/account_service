package faang.school.accountservice.entity;

import faang.school.accountservice.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency; //TODO непонятно пока, нужно подумать, если я делаю таблицу,
    // и к ней сущность, то тогда мне нужно удалить CurrencyType в пакете энамс, он же не нужен получается

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "status_details", length = 128)
    private String statusDetails;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(name = "closed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime closingDate;

    @Version
    @Column(name = "version")
    private long version;
}
