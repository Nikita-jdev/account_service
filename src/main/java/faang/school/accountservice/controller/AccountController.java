package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public AccountDto openAccount(@RequestBody AccountDto accountDto) {
        return accountService.openAccount(accountDto);
    }

    @GetMapping("/{id}")
    public AccountDto getAccount(@PathVariable long id) {
        return accountService.getAccount(id);
    }

    @PutMapping("/{id}/block")
    public AccountDto blockAccount(@PathVariable long id) {
        return accountService.blockAccount(id);
    }

    @PutMapping("/{id}/close")
    public AccountDto closeAccount(@PathVariable long id) {
        return accountService.closeAccount(id);
    }
}
