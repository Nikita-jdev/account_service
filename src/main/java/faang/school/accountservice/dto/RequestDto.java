package faang.school.accountservice.dto;

import faang.school.accountservice.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private UUID token;
    private long userId;
    private OperationType operationType;
    private Map<String, Object> inputData;
    private String operationStatusDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}