package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.dto.CreateAccountDto;
import faang.school.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "ownerId", source = "accountOwner.id")
    AccountDto toDto(Account account);

    @Mapping(target = "accountOwner", ignore = true)
    Account toEntity(AccountDto accountDto);

    @Mapping(target = "accountOwner", ignore = true)
    Account toEntity(CreateAccountDto accountDto);
}
