package faang.school.accountservice.dto;

import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DmsEvent {
    @NotNull
    private Long senderId;
    @NotNull
    private String senderAccountNumber;
    @NotNull
    private String receiverAccountNumber;
    @NotNull
    private Money money;
    @NotNull
    private RequestStatus requestStatus;
    @NotNull
    private RequestType requestType;
}
