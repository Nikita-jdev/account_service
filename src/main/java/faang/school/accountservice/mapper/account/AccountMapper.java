package faang.school.accountservice.mapper.account;

import faang.school.accountservice.dto.account.AccountDto;
import faang.school.accountservice.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    AccountDto toDto(Account account);

    @Mapping(source = "ownerId", target = "owner.id")
    Account toEntity(AccountDto accountDto);

    List<AccountDto> toDto(List<Account> accounts);
}
