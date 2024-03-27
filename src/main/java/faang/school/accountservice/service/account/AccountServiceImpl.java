package faang.school.accountservice.service.account;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.exception.AccountOperationException;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.numberGenerator.RandomNumberGenerator;
import faang.school.accountservice.service.owner.OwnerService;
import faang.school.accountservice.service.request.RequestExecutorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
//      @Value("${random.number-generator.min}") //почему то ругается и кидает UnsatisfiedDependencyException
    private static final int MIN_DIGITS = 12; // поэтому константы!
    private static final int MAX_DIGITS = 20;

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final OwnerService ownerService;
    private final RandomNumberGenerator randomNumberGenerator;

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccount(long accountId) {
        Account account = getAccountById(accountId);
        log.info("Получен счёт с ID: {} и  №: {}", account.getId(), account.getNumber());
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 5)
    public AccountDto create(AccountDto accountDto) {
        log.info("Попытка открыть новый счёт для владельца с ID: {}, c №: {}, типом: {}, валютой: {}",
                accountDto.getOwnerId(), accountDto.getNumber(), accountDto.getType(), accountDto.getCurrency());

        Owner owner = fetchOrCreateOwner(accountDto);
        accountDto.setOwnerType(owner.getOwnerType());
        accountDto.setOwnerId(owner.getId());

        String accountNumber = generateUniqueAccountNumber(accountDto.getNumber());
        accountDto.setNumber(accountNumber);

        Account account = accountMapper.toEntity(accountDto);
        account.setStatus(AccountStatus.ACTIVE);
        account = accountRepository.save(account);
        log.info("создан новый счет с №: {} и ID: {}",
                account.getNumber(), account.getId());
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 5)
    public AccountDto blockAccount(long id) {
        Account account = getAccountById(id);

        if (isAccountClosedForTransactions(account)) {
            throw new AccountOperationException("Аккаунт был закрыт ранее в: " + account.getClosedAt());
        }

        log.info("Попытка изменить статус аккаунт c ID: {}  и № счета {}, на статус BLOCKED",
                account.getId(), account.getNumber());
        account.setStatus(AccountStatus.BLOCKED);
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 5)
    public AccountDto closeAccount(long id) {
        Account account = getAccountById(id);

        if (isAccountClosedForTransactions(account)) {
            log.info("Aккаунт с ID: {}, был закрыт ранее в: {}",
                    account.getId(), account.getClosedAt());
            throw new AccountOperationException("Аккаунт был закрыт ранее в: " + account.getClosedAt());
        }

        account.setStatus(AccountStatus.CLOSED);
        account.setClosedAt(LocalDateTime.now());
        log.info("Аккаунт с ID: {}, закрыт  в {}",
                account.getId(), account.getClosedAt());
        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto deleteAccount(AccountDto accountDto) {
        Account account = accountRepository.findByOwnerId(accountDto.getOwnerId())
                .orElseThrow(() -> new faang.school.accountservice.exception.EntityNotFoundException("Account not found by owner id"));

        accountRepository.delete(account);
        return accountDto;
    }


    private Account getAccountById(long id) {
        log.info("Попытка найти аккаунт c ID: {}", id);
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт с ID: " + id + " не найден"));
    }

    private boolean isAccountClosedForTransactions(Account account) {
        return (account.getStatus().equals(AccountStatus.CLOSED));
    }

    private Owner fetchOrCreateOwner(AccountDto accountDto) {
        log.info("Ищем владельца с ID: {}, если нет, то создаем нового c типом: {}",
                accountDto.getOwnerId(), accountDto.getOwnerType());
        return ownerService.findById(accountDto.getOwnerId())
                .orElseGet(() -> ownerService.saveOwner(accountDto.getOwnerType()));
    }

    private String generateUniqueAccountNumber(String proposedNumber) {
        if (proposedNumber != null && !accountRepository.existsByNumber(proposedNumber)) {
            log.info("Желаемый номер: {}, свободен", proposedNumber);
            return proposedNumber;
        }
        log.info("Желаемый №: {} уже занят. Начинаем генерировать новый № ...", proposedNumber);

        String generatedNumber;
        do {
            generatedNumber = randomNumberGenerator.generateRandomNumber(MIN_DIGITS, MAX_DIGITS);
        } while (accountRepository.existsByNumber(generatedNumber));

        log.info("Новый сгенерированный номер: {}", generatedNumber);
        return generatedNumber;
    }

}