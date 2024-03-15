package faang.school.accountservice.controller;

import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.service.FreeAccountNumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-numbers")
@RequiredArgsConstructor
public class FreeAccountNumberController {
    private final FreeAccountNumbersService freeAccountNumbersService;

    @PostMapping("/generate/{count}")
    public void generateFreeAccountNumbers(@PathVariable int count,
                                           @RequestBody AccountType type) {
        freeAccountNumbersService.generateNumber(count, type);
    }
}

