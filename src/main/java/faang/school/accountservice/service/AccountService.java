package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    @Transactional
    public AccountDto openAccount(AccountDto accountDto) {

        return null;
    }
}
