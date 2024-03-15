package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.account.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountDto get(@PathVariable
                          @Positive(message = "ID должно быть положительное") long id) {
        log.info("Получили запрос на получение аккаунта с");
        return accountService.getAccount(id);
    }

    @PostMapping
    public AccountDto open(@Valid @RequestBody AccountDto accountDto) {
        log.info("Получили запрос на создание аккаунта c №: {}, типом: {}, валютой: {} и владельцем с ID: {}",
                accountDto.getNumber(), accountDto.getType(), accountDto.getCurrency(), accountDto.getOwnerId());
        return accountService.create(accountDto);
    }

    @PutMapping("/{id}/block")
    public AccountDto block(@PathVariable
                            @Positive(message = "ID должно быть положительное") long id) {
        log.info("Получили запрос на блокировку аккаунта с ID: {}", id);
        return accountService.blockAccount(id);
    }

    @PutMapping("/{id}/close")
    public AccountDto close(@PathVariable
                            @Positive(message = "ID должно быть положительное") long id) {
        log.info("Получили запрос на закрытие аккаунта с ID: {}", id);
        return accountService.closeAccount(id);
    }

}