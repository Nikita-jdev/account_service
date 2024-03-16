package faang.school.accountservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.accountservice.dto.DmsEvent;
import faang.school.accountservice.enums.RequestType;
import faang.school.accountservice.service.dms.DmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DmsEventListener implements MessageListener {
    private final ObjectMapper objectMapper;
    private final DmsService dmsService;
    private DmsEvent dmsEvent;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            dmsEvent = objectMapper.readValue(message.getBody(), DmsEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("Error during objectMapper.readValue "+e);
        }

        if (dmsEvent.getRequestType().equals(RequestType.AUTHORIZATION)) {
            dmsService.authorizationTransaction(dmsEvent);
        } else if (dmsEvent.getRequestType().equals(RequestType.CANCEL)) {
            dmsService.cancelTransaction(dmsEvent);
        } else if (dmsEvent.getRequestType().equals(RequestType.CLEARING)) {
            dmsService.forcedTransaction(dmsEvent);
        }
    }
}
