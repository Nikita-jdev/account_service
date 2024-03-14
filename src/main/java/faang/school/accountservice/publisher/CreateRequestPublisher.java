package faang.school.accountservice.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.accountservice.dto.CreateRequestEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class CreateRequestPublisher extends AbstractEventPublisher<CreateRequestEvent>{

    @Value("${spring.data.redis.channels.create_request_channel.name}")
    private String createRequestChannel;

    public CreateRequestPublisher(ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate) {
        super(objectMapper, redisTemplate);
    }

    @Override
    public void publish(CreateRequestEvent event) {
        convertAndSend(event, createRequestChannel);
    }
}
