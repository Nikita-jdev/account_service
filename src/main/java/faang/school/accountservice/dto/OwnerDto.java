package faang.school.accountservice.dto;

import faang.school.accountservice.enums.OwnerType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerDto {

    private long id;
    @NotNull(message = "Owner id must not be null")
    private long ownerId;
    @NotNull(message = "Owner type must not be null")
    private OwnerType ownerType;
}
