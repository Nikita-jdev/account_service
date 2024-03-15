package faang.school.accountservice.service.account;

import faang.school.accountservice.dto.AccountDto;

public interface AccountService {

    AccountDto getAccount(long id);

    AccountDto create(AccountDto accountDto);

    AccountDto blockAccount(long id);

    AccountDto closeAccount(long id);

}