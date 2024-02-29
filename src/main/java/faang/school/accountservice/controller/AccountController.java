package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountDto get(@PathVariable long id) {
        return accountService.get(id);
    }

    @PostMapping("/open")
    public AccountDto open(@RequestBody @Valid AccountDto accountDto) {
        return accountService.open(accountDto);
    }

    @PostMapping("/close/{id}")
    public AccountDto close(@PathVariable long id) {
        return accountService.close(id);
    }

    @PostMapping("/block/{id}")
    public AccountDto block(@PathVariable long id,
                            @RequestParam Status status) {
        return accountService.block(id, status);
    }
}
