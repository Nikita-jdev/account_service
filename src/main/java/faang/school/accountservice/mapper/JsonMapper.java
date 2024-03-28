package faang.school.accountservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonMapper implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return objectMapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            log.info("Json processing exception, convert to DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, Map.class);
        } catch (JsonProcessingException e) {
            log.info("Json processing exception, convert to entity", e);
            throw new RuntimeException(e);
        }
    }
}
