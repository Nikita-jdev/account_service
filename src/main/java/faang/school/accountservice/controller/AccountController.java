package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/open")
    public AccountDto open(@RequestBody @Valid AccountDto accountDto) {
        return accountService.open(accountDto);
    }

    @GetMapping("/{accountId}")
    public AccountDto get(@PathVariable long accountId) {
        return accountService.get(accountId);
    }

    @PutMapping("/block/{accountId}")
    public void block(@PathVariable long accountId){
        accountService.block(accountId);
    }

    @PutMapping("/unblock/{accountId}")
    public void unBlock(@PathVariable long accountId){
        accountService.unBlock(accountId);
    }

    @DeleteMapping("/{accountId}")
    public void delete(@PathVariable long accountId){
        accountService.delete(accountId);
    }
}
