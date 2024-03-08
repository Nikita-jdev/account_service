package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.RequestDto;
import faang.school.accountservice.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {
    Request toEntity (RequestDto requestDto);

    RequestDto toDto (Request request);
}
