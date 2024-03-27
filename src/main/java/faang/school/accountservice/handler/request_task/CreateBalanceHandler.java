package faang.school.accountservice.handler.request_task;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.balance.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBalanceHandler implements RequestTaskHandler{
    private final BalanceService balanceService;

    @Override
    public String getHandlerId() {
        return "create balance";
    }

    @Override
    public void execute(AccountDto accountDto) {
        balanceService.createBalance(accountDto.getOwnerId());
    }

    @Override
    public void rollback(AccountDto accountDto) {
        balanceService.deleteBalance(accountDto.getNumber());
    }
}
