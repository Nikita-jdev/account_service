package faang.school.accountservice.controller;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Mock
    private AccountService accountService;
    @InjectMocks
    private AccountController accountController;

    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        accountDto = AccountDto.builder()
                .ownerId(1L)
                .accountType("DEBIT")
                .currency("USD")
                .build();
    }

    @Test
    void shouldOpenAccount() {
        Mockito.when(accountService.openAccount(accountDto)).thenReturn(accountDto);
        accountController.openAccount(accountDto);
        Mockito.verify(accountService, Mockito.times(1)).openAccount(accountDto);
    }

    @Test
    void getAccount() {
    }
}