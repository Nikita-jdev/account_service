package faang.school.accountservice.controller;

import faang.school.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private AccountService accountService;

    public int getBalance(int accountNumber) {
        return 0;
    }

}
