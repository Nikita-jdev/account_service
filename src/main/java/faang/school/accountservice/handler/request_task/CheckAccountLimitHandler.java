package faang.school.accountservice.handler.request_task;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.service.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CheckAccountLimitHandler implements RequestTaskHandler{

    private final OwnerService ownerService;

    @Value("${account.limit}")
    private int accountLimit;

    @Override
    public String getHandlerId() {
        return "check account limit";
    }

    @Override
    public void execute(AccountDto accountDto) {
        long ownerId = accountDto.getOwnerId();

        Owner owner = ownerService.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        int ownerAccountsSize = owner.getAccounts().size();

        if (ownerAccountsSize > accountLimit) {
            throw new IllegalArgumentException("The owner's account limit has been exceeded");
        }
    }

    @Override
    public void rollback(AccountDto accountDto) {

    }
}
