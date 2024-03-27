package faang.school.accountservice.handler.request_task;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountHandler implements RequestTaskHandler {
    private final AccountService accountService;

    @Override
    public String getHandlerId() {
        return "create account";
    }

    @Override
    public void execute(AccountDto accountDto) {
        accountService.create(accountDto);
    }

    @Override
    public void rollback(AccountDto accountDto) {
        accountService.deleteAccount(accountDto);
    }
}
