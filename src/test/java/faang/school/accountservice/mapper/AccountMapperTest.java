package faang.school.accountservice.mapper;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Owner;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.Status;
import faang.school.accountservice.enums.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    private final AccountMapper accountMapper = new AccountMapperImpl();

    private Account account;
    private AccountDto accountDto;
    private Owner owner;

    @BeforeEach
    public void setUp(){
        LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        owner = Owner.builder()
                .id(1L)
                .build();

        account = Account.builder()
                .id(1L)
                .accountNumber("0000000000000000")
                .accountOwner(null)
                .accountType(Type.DEBIT)
                .currency(Currency.USD)
                .status(Status.ACTIVE)
                .createdAt(instant)
                .updatedAt(instant)
                .closedAt(null)
                .build();

        accountDto = AccountDto.builder()
                .id(1L)
                .accountNumber("0000000000000000")
                .ownerId(null)
                .accountType(Type.DEBIT)
                .currency(Currency.USD)
                .status(Status.ACTIVE)
                .createdAt(instant)
                .updatedAt(instant)
                .closedAt(null)
                .build();
    }

    @Test
    void toDto() {
        account.setAccountOwner(owner);
        accountDto.setOwnerId(owner.getId());
        assertEquals(accountDto, accountMapper.toDto(account));
    }

    @Test
    void toEntity() {
        assertEquals(account, accountMapper.toEntity(accountDto));
    }
}