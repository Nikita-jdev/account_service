package faang.school.accountservice.entity;

import faang.school.accountservice.entity.converter.HashMapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "lock_value")
    private String lockValue;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "input_data", columnDefinition = "jsonb")
    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> inputData;

    @Column(name = "status")
    private String status;

    @Column(name = "status_details")
    private String statusDetails;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "version")
    private int version;
}