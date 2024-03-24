package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final FreeAccountNumbersService freeAccountNumbersService;

    public AccountDto get(long id) {
        return accountMapper.toDto(getAccount(id));
    }

    @Retryable(retryFor = OptimisticLockException.class)
    public AccountDto open(AccountDto accountDto) {
        String number = freeAccountNumbersService.getNumber(accountDto.getType(),
                n -> System.out.println("Generated number account number: " + n));
        accountDto.setStatus(Status.ACTIVE);
        accountDto.setNumber(number);
        Account newAccount = accountRepository.save(accountMapper.toEntity(accountDto));
        return accountMapper.toDto(newAccount);
    }

    @Retryable(retryFor = OptimisticLockException.class)
    @Transactional
    public AccountDto close(long id) {
        Account account = getAccount(id);
        account.setStatus(Status.CLOSED);
        account.setClosedAt(Instant.now());
        return accountMapper.toDto(account);
    }

    @Retryable(retryFor = OptimisticLockException.class)
    @Transactional
    public AccountDto block(long id, Status status) {
        blockStatusValidate(status);
        Account account = getAccount(id);
        account.setStatus(status);
        return accountMapper.toDto(account);
    }

    public Account getAccount(long id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account doesn't exist by id: %s", id)));
    }

    public void blockStatusValidate(Status status) {
        if (status != Status.BLOCKED && status != Status.SUSPENDED) {
            throw new IllegalArgumentException("You can use only BLOCKED or SUSPENDED status");
        }
    }
}