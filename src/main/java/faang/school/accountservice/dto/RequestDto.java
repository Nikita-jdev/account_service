package faang.school.accountservice.dto;

import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import faang.school.accountservice.enums.RollbackStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    @NotNull
    private UUID id;
    @NotNull
    private Long userId;
    @NotNull
    private RequestType requestType;
    private RequestStatus requestStatus;
    private RollbackStatus rollbackStatus;
}
