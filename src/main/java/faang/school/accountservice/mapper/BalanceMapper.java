package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.model.account.Account;
import faang.school.accountservice.model.balance.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceMapper {

    Balance toEntity(BalanceDto balanceDto);

    @Mapping(target = "accountId", source = "account", qualifiedByName = "accountToAccountId")
    BalanceDto toDto(Balance balance);

    @Named("accountToAccountId")
    default long accountToAccountId(Account account) {
        return account.getId();
    }
}
