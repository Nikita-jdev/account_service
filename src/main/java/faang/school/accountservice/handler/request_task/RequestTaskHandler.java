package faang.school.accountservice.handler.request_task;

import faang.school.accountservice.dto.AccountDto;


public interface RequestTaskHandler {
    String getHandlerId();

    void execute(AccountDto accountDto);

    void rollback(AccountDto accountDto);
}
