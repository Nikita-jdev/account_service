package faang.school.accountservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request")
public class Request {

    @Id
    private String token;

    @Column(name = "user_id", nullable = false, unique = true)
    private long user_id;

    @Column(name = "request_type", length = 60, nullable = false)
    private String request_type;

    @Column(name = "lock_value", nullable = false)
    private long lock_value;

    @Column(name = "is_open")
    private boolean is_open;

    @Column(name = "request_data", nullable = false)
    private String request_data;

    @Column(name = "request_status", length = 60, nullable = false)
    private String request_status;

    @Column(name = "addit_status")
    private String addit_status;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "version")
    private int version;
}
