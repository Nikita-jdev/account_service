package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.AccountStatus;
import faang.school.accountservice.enums.AccountType;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.OwnerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    @Spy
    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    private AccountDto accountDto;
    private Account account;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = Owner.builder()
                .id(1L)
                .ownerType(OwnerType.USER)
                .build();

        accountDto = AccountDto.builder()
                .id(1L)
                .type(AccountType.DEBIT)
                .number("1234567890")
                .ownerId(owner.getId())
                .ownerType(owner.getOwnerType())
                .currency(Currency.RUB)
                .status(AccountStatus.ACTIVE)
                .build();

        account = Account.builder()
                .id(1L)
                .accountType(AccountType.DEBIT)
                .number("1234567890")
                .owner(owner)
                .currency(Currency.RUB)
                .status(AccountStatus.ACTIVE)
                .build();
    }

    @Test
    void testToDto() {
        AccountDto actual = accountMapper.toDto(account);
        assertEquals(accountDto, actual);
    }

    @Test
    void testToEntity() {
        Account actual = accountMapper.toEntity(accountDto);
        assertEquals(account, actual);
    }

}