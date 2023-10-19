package faang.school.accountservice.dto;

import faang.school.accountservice.entity.TariffType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    private Long id;
    private TariffType tariffType;
    private BigDecimal rate;
}