package faang.school.accountservice.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.accountservice.dto.OpenAccountEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class OpenAccountPublisher extends AbstractEventPublisher<OpenAccountEvent>{

    @Value("${spring.data.redis.channels.open_account_channel.name}")
    private String openAccountChannelName;

    public OpenAccountPublisher(ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate) {
        super(objectMapper, redisTemplate);
    }

    @Override
    public void publish(OpenAccountEvent event) {
        convertAndSend(event, openAccountChannelName);
    }
}
