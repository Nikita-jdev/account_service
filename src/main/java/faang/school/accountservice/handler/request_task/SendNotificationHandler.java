package faang.school.accountservice.handler.request_task;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.OpenAccountEvent;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.publisher.OpenAccountPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class SendNotificationHandler implements RequestTaskHandler {
    private final OpenAccountPublisher openAccountPublisher;

    @Override
    public String getHandlerId() {
        return "send notification";
    }

    @Override
    public void execute(AccountDto accountDto) {
        OpenAccountEvent openAccountEvent = OpenAccountEvent.builder()
                .accountOpenedTime(LocalDateTime.now())
                .accountStatus(AccountStatus.ACTIVE)
                .userId(accountDto.getOwnerId())
                .build();
        openAccountPublisher.publish(openAccountEvent);
    }

    @Override
    public void rollback(AccountDto accountDto) {

    }
}
