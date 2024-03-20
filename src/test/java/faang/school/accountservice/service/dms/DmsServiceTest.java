package faang.school.accountservice.service.dms;

import faang.school.accountservice.dto.DmsEvent;
import faang.school.accountservice.dto.Money;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.Currency;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceRepository;
import faang.school.accountservice.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DmsServiceTest {
    @InjectMocks
    private DmsService dmsService;
    @Mock
    private BalanceRepository balanceRepository;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private AccountRepository accountRepository;

    DmsEvent dmsEvent;
    Request request;
    Account account;
    Balance balance;

    @BeforeEach
    void setUp() {
        dmsEvent = DmsEvent.builder()
                .senderId(1L)
                .requestStatus(RequestStatus.PENDING)
                .senderAccountNumber("12345")
                .receiverAccountNumber("54321")
                .money(new Money(BigDecimal.ONE, Currency.USD))
                .build();

        request = Request.builder()
                .userId(dmsEvent.getSenderId())
                .isOpenRequest(false)
                .lockValue((dmsEvent.getSenderId() + dmsEvent.getSenderAccountNumber() + dmsEvent.getMoney().amount()))
                .requestStatus(RequestStatus.PENDING)
                .requestType(RequestType.AUTHORIZATION)
                .build();

        account = Account.builder()
                .number("12345")
                .build();

        balance = Balance.builder()
                .account(account)
                .authorizationBalance(BigDecimal.valueOf(1000))
                .clearingBalance(BigDecimal.valueOf(1000))
                .build();
    }


    @Test
    void testAuthorizationTransaction() {
        prepareData();
        dmsService.authorizationTransaction(dmsEvent);
        assertEquals("1123451", request.getLockValue());
        assertEquals(RequestStatus.PENDING, request.getRequestStatus());
    }

    @Test
    void testCancelTransaction() {
        prepareData();
        when(requestRepository.findByLockValue(anyString())).thenReturn(Optional.of(request));

        dmsService.cancelTransaction(dmsEvent);
        assertEquals(RequestStatus.SUCCESS, request.getRequestStatus());
        assertEquals(RequestType.CANCEL, request.getRequestType());
    }

    @Test
    void testForcedTransaction() {
        prepareData();
        when(requestRepository.findByLockValue(anyString())).thenReturn(Optional.of(request));

        dmsService.forcedTransaction(dmsEvent);
        assertEquals(RequestStatus.SUCCESS, request.getRequestStatus());
        assertEquals(RequestType.CLEARING, request.getRequestType());
    }

    private void prepareData() {
        when(accountRepository.findByNumber("12345")).thenReturn(Optional.of(account));
        when(accountRepository.findByNumber("54321")).thenReturn(Optional.of(account));
        when(balanceRepository.findByAccountNumber(account.getNumber())).thenReturn(Optional.of(balance));
    }
}
