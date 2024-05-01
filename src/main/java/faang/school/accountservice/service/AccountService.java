package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.model.Account;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validation.AccountValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountValidate accountValidate;

    @Transactional
    public AccountDto open(AccountDto accountDto){
        Account account = accountMapper.toEntity(accountDto);
        accountValidate.validate(account);
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountDto get(long accountId){
        return accountMapper.toDto(findAccountById(accountId));
    }

    @Transactional
    public void block(long accountId){
        Account account = findAccountById(accountId);
        account.setAccountStatus(AccountStatus.FROZEN);
        accountRepository.save(account);
    }

    @Transactional
    public void unBlock(long accountId){
        Account account = findAccountById(accountId);
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @Transactional
    public void delete(long accountId){
        Account account = findAccountById(accountId);
        account.setAccountStatus(AccountStatus.CLOSED);
        accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findAccountById(long accountId){
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with id: %d not found", accountId)));
    }
}
