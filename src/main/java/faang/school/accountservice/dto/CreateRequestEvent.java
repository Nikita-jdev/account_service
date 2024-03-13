package faang.school.accountservice.dto;

import faang.school.accountservice.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestEvent {
    private Long userId;
    private RequestStatus requestStatus;
    private LocalDateTime requestCreatedTime;
}
