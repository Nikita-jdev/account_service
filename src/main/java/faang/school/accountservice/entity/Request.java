package faang.school.accountservice.entity;

import faang.school.accountservice.entity.converter.HashMapConverter;
import faang.school.accountservice.enums.OperationStatus;
import faang.school.accountservice.enums.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", columnDefinition = "UUID")
    private UUID token;

    @Column(name = "user_id")
    private long userId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @Column(name = "lock_value")
    private Long lockValue;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "input_data", columnDefinition = "jsonb")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> inputData;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "operation_status", nullable = false)
    private OperationStatus operationStatus;

    @Column(name = "status_details")
    private String statusDetails;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}