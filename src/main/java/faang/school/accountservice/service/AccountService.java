package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final FreeAccountNumbersService freeAccountNumbersService;
    private final OwnerService ownerService;

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3)
    public AccountDto openAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        account.setAccountNumber(freeAccountNumbersService.getFreeNumber(accountDto.getAccountType()));
        account.setStatus(Status.ACTIVE);
        account.setAccountOwner(ownerService.findById(accountDto.getOwnerId()));
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3)
    public AccountDto getAccount(long id) {
        return accountMapper.toDto(findById(id));
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3)
    public AccountDto blockAccount(long id) {
        Account account = findById(id);
        account.setStatus(Status.FROZEN);
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3)
    public AccountDto closeAccount(long id) {
        Account account = findById(id);
        account.setStatus(Status.CLOSED);
        return accountMapper.toDto(accountRepository.save(account));
    }

    public Account findById(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }

}
