package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.RequestTemplate;
import faang.school.accountservice.entity.Request;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {

    @Mapping(target = "requestData", expression = "java(ObjectToMapConverter.convertObjectToMap(requestTemplate.getRequestData()))")
    Request toEntity(RequestTemplate requestTemplate);

    RequestTemplate toRequestTemplate(Request request);
}
