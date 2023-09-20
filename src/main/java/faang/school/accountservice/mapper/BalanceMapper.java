package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.balance.BalanceDto;
import faang.school.accountservice.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceMapper {

    Balance toEntity(BalanceDto balanceDto);
    @Mapping(target = "accountId", source = "account.id")
    BalanceDto toDto(Balance balance);
}
