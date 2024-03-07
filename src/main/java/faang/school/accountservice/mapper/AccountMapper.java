package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountDto toDto(Account account);

    @Mapping(target = "accountOwner", source = "accountOwner.id")
    Account toEntity(AccountDto accountDto);
}
