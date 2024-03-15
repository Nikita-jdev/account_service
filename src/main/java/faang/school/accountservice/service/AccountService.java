package faang.school.accountservice.service;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.CreateAccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.enums.Type;
import faang.school.accountservice.exception.DataValidationException;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final FreeAccountNumbersService freeAccountNumbersService;
    private final OwnerService ownerService;
    private final AccountValidator accountValidator;

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3)
    public AccountDto openAccount(CreateAccountDto createAccountDto) {
        Account account = accountMapper.toEntity(createAccountDto);
        account.setAccountType(convertType(createAccountDto.getAccountType()));
        account.setCurrency(convertCurrency(createAccountDto.getCurrency()));
        account.setAccountNumber(freeAccountNumbersService.getFreeNumber(createAccountDto.getAccountType()));
        account.setStatus(Status.ACTIVE);
        account.setAccountOwner(ownerService.findById(createAccountDto.getOwnerId()));
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
        accountValidator.validateNotFrozen(account);
        account.setStatus(Status.FROZEN);
        return accountMapper.toDto(account);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3)
    public AccountDto closeAccount(long id) {
        Account account = findById(id);
        account.setStatus(Status.CLOSED);
        return accountMapper.toDto(account);
    }

    public Account findById(long id) {
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    //Два одинаковых метода. Можно сделать один общий, только не знаю в какой класс его поместить
    public Type convertType(String type) {
        if (!type.isBlank()) {
            try {
                return Type.valueOf(type);
            } catch (IllegalArgumentException e) {
                throw new DataValidationException("Введено некорректное значение для параметра Type :" +
                        type + "\nДопустимые значения: " + Arrays.toString(Type.values()));
            }
        }
        return null;
    }

    public Currency convertCurrency(String currency) {
        if (!currency.isBlank()) {
            try {
                return Currency.valueOf(currency);
            } catch (IllegalArgumentException e) {
                throw new DataValidationException("Введено некорректное значение для параметра Type :" +
                        currency + "\nДопустимые значения: " + Arrays.toString(Currency.values()));
            }
        }
        return null;
    }

}
