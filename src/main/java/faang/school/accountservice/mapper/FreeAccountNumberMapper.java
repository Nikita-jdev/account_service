package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.FreeAccountNumberDto;
import faang.school.accountservice.model.FreeAccountNumber;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FreeAccountNumberMapper {
    FreeAccountNumberDto toDto(FreeAccountNumber freeAccountNumber);
    FreeAccountNumber toEntity(FreeAccountNumberDto freeAccountNumberDto);
}
