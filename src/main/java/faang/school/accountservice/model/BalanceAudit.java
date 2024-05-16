package faang.school.accountservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance_audit")
public class BalanceAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "auth_balance")
    private long authorizedBalance;
    @Column(name = "actual_balance")
    private long actualBalance;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Instant createdAt;
    @Version
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq")
    @Column(name = "balance_version")
    private long balanceVersion;
    @Version
    @Column(name = "operation_id")
    private long operationId;
}
