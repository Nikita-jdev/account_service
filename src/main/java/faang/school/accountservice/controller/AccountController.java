package faang.school.accountservice.controller;

import faang.school.accountservice.config.context.UserContext;
import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account")
    public AccountDto openAccount(@RequestBody AccountDto accountDto) {
        return accountService.openAccount(accountDto);
    }

    @GetMapping("/account/{id}")
    public AccountDto getAccount(@PathVariable long id) {
        return accountService.getAccount(id);
    }

    @PutMapping("{id}/block")
    public AccountDto blockAccount(@PathVariable long id) {
        return accountService.blockAccount(id);
    }

}
