package faang.school.accountservice.dto;

import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTemplate {
    private long ownerId;
    private OwnerType ownerType;
    private RequestType requestType;
    private long lockValue;
    private Object requestData; // entity
    private RequestStatus requestStatus;
    @Nullable
    private String statusDetails;
}
