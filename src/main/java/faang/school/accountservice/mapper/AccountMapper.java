package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "accountType", target = "type")
    @Mapping(source = "owner.ownerType", target = "ownerType")
    AccountDto toDto(Account account);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "type", target = "accountType")
    @Mapping(source = "ownerType", target = "owner.ownerType")
    Account toEntity(AccountDto accountDto);

}