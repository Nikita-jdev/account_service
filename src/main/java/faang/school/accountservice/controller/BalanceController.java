package faang.school.accountservice.controller;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/account/balance/{accountId}")
    public BalanceDto getBalance(@PathVariable long accountId) {
        return balanceService.getBalance(accountId);
    }
}
