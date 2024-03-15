package faang.school.accountservice.controller;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.service.balance.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/balances")
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping("/{accountId}")
    public BalanceDto createBalance(@PathVariable long accountId) {
        return balanceService.createBalance(accountId);
    }

    @PutMapping("/{balanceId}")
    public BalanceDto updateBalance(@PathVariable long balanceId, @RequestParam("deposit") BigDecimal deposit) {
        return balanceService.updateBalance(balanceId, deposit);
    }

    @GetMapping("/{balanceId}")
    public BalanceDto getBalance(@PathVariable long balanceId) {
        return balanceService.getBalance(balanceId);
    }
}
