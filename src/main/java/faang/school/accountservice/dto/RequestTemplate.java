package faang.school.accountservice.dto;

import faang.school.accountservice.enums.OwnerType;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTemplate {
    @Setter(AccessLevel.NONE)
    private long id; // нужно ли оно?
    private long ownerId;
    private OwnerType ownerType;
    private RequestType requestType;
    private long lockValue;
    private Object requestData; // entity
    private RequestStatus requestStatus;
    @Nullable
    private String statusDetails;
}
