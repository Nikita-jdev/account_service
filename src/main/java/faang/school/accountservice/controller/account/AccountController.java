package faang.school.accountservice.controller.account;

import faang.school.accountservice.config.context.UserContext;
import faang.school.accountservice.dto.account.AccountDto;
import faang.school.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final UserContext userContext;
    private final AccountService accountService;

    @PostMapping
    public AccountDto open(@RequestBody @Valid AccountDto accountDto) {
        long userId = userContext.getUserId();
        return accountService.open(userId, accountDto);
    }

    @GetMapping("/{accountId}")
    public AccountDto get(@PathVariable long accountId) {
        long userId = userContext.getUserId();
        return accountService.get(userId, accountId);
    }

    @GetMapping
    public List<AccountDto> getAllOwnerAccounts(){
        long userId = userContext.getUserId();
        return accountService.getAllOwnerAccounts(userId);
    }

    @PutMapping("{accountId}/block")
    public void block(@PathVariable long accountId) {
        long userId = userContext.getUserId();
        accountService.block(userId, accountId);
    }

    @PutMapping("{accountId}/close")
    public void close(@PathVariable long accountId) {
        long userId = userContext.getUserId();
        accountService.close(userId, accountId);
    }
}
