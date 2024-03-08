package faang.school.accountservice.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {
        try {
            return objectMapper.writeValueAsString(customerInfo);
        } catch (JsonProcessingException e) {
            log.error("JSON writing error", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {
        try {
            return objectMapper.readValue(
                    customerInfoJSON, new TypeReference<HashMap<String, Object>>() {}
            );
        } catch (IOException e) {
            log.error("JSON reading error", e);
        }
        return null;
    }
}