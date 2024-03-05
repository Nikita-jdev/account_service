package faang.school.accountservice.entity;

import faang.school.accountservice.config.converter.MapToStringConverter;
import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UUID;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @UUID
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "owner_id", unique = true)
    private long ownerId;

    @Column(name = "owner_type", length = 7, nullable = false)
    @Enumerated(EnumType.STRING)
    private OwnerType ownerType;

    @Column(name = "request_type", length = 64, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Column(name = "lock_value", nullable = false)
    private long lockValue;

    @Column(name = "is_open", nullable = false)
    private boolean isOpen;

    @Column(name = "request_data", nullable = false)
    @Convert(converter = MapToStringConverter.class)
    private Map<String,Object> requestData;

    @Column(name = "request_status", length = 60, nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @Column(name = "status_details", length = 128)
    private String statusDetails;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    private int version;
}
