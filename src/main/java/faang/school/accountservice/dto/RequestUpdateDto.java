package faang.school.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUpdateDto {
    private UUID token;
    private Map<String, Object> inputData;
    private String statusDetails;
}