package faang.school.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import faang.school.accountservice.enums.TariffType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TariffDto {
    private long id;
    private TariffType type;
    private float ratePercent;
    @JsonFormat(pattern = "dd:MM.yyyy'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd:MM.yyyy'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
