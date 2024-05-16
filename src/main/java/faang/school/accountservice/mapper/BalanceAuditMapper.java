package faang.school.accountservice.mapper;


import faang.school.accountservice.dto.BalanceAuditDto;
import faang.school.accountservice.model.BalanceAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceAuditMapper {

    @Mapping(source = "account.id", target = "accountId")
    BalanceAuditDto toDto(BalanceAudit balanceAudit);

    @Mapping(target = "account", ignore = true)
    BalanceAudit toEntity(BalanceAuditDto balanceAuditDto);
}