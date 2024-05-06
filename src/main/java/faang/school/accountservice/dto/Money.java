package faang.school.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import faang.school.accountservice.entity.Currency;

import java.math.BigDecimal;

public record Money(
        @JsonProperty(value = "amount", required = true)
        BigDecimal amount,
        @JsonProperty(value = "currency", required = true)
        Currency currency //TODO: Currency был тут из пакета enums, я поменял на свой,
        // пока не совсем понимаю что лучше использовать, сущность или енам
) {
}
