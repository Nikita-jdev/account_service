package faang.school.accountservice.service.dms;

import faang.school.accountservice.dto.DmsEvent;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Balance;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.BalanceRepository;
import faang.school.accountservice.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DmsService {
    private final BalanceRepository balanceRepository;
    private final AccountRepository accountRepository;
    private final RequestRepository requestRepository;

    @Transactional
    public void authorizationTransaction(DmsEvent dmsEvent) {
        Balance senderBalance = getBalanceByNumber(dmsEvent.getSenderAccountNumber());
        Balance receiverBalance = getBalanceByNumber(dmsEvent.getReceiverAccountNumber());
        validatePaymentPossible(senderBalance, dmsEvent.getMoney().amount());

        BigDecimal senderAuthBalance = senderBalance.getAuthorizationBalance().subtract(dmsEvent.getMoney().amount());
        BigDecimal receiverAuthBalance = receiverBalance.getAuthorizationBalance().add(dmsEvent.getMoney().amount());
        senderBalance.setAuthorizationBalance(senderAuthBalance);
        receiverBalance.setAuthorizationBalance(receiverAuthBalance);

        balanceRepository.saveAll(List.of(senderBalance, receiverBalance));

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("amount", dmsEvent.getMoney().amount());
        inputData.put("currency", dmsEvent.getMoney().currency());
        inputData.put("senderAccountNumber", dmsEvent.getSenderAccountNumber());
        inputData.put("receiverAccountNumber", dmsEvent.getReceiverAccountNumber());

        Request request = Request.builder()
                .userId(dmsEvent.getSenderId())
                .isOpenRequest(false)
                .lockValue((dmsEvent.getSenderId() + dmsEvent.getSenderAccountNumber() + dmsEvent.getMoney().amount()))
                .requestStatus(RequestStatus.PENDING)
                .requestType(RequestType.AUTHORIZATION)
                .inputData(inputData)
                .build();

        requestRepository.save(request);
    }

    @Transactional
    public void cancelTransaction(DmsEvent dmsEvent) {
        Balance senderBalance = getBalanceByNumber(dmsEvent.getSenderAccountNumber());
        Balance receiverBalance = getBalanceByNumber(dmsEvent.getReceiverAccountNumber());

        BigDecimal senderAuthBalance = senderBalance.getAuthorizationBalance().add(dmsEvent.getMoney().amount());
        BigDecimal receiverAuthBalance = receiverBalance.getAuthorizationBalance().subtract(dmsEvent.getMoney().amount());
        senderBalance.setAuthorizationBalance(senderAuthBalance);
        receiverBalance.setAuthorizationBalance(receiverAuthBalance);

        balanceRepository.saveAll(List.of(senderBalance, receiverBalance));

        Request request = getRequest(dmsEvent);
        request.setRequestStatus(RequestStatus.SUCCESS);
        request.setRequestType(RequestType.CANCEL);
        request.setOpenRequest(false);

        requestRepository.save(request);
    }

    @Transactional
    public void forcedTransaction(DmsEvent dmsEvent) {
        Balance senderBalance = getBalanceByNumber(dmsEvent.getSenderAccountNumber());
        Balance receiverBalance = getBalanceByNumber(dmsEvent.getReceiverAccountNumber());

        BigDecimal senderClearingBalance = senderBalance.getClearingBalance().subtract(dmsEvent.getMoney().amount());
        BigDecimal receiverClearingBalance = receiverBalance.getClearingBalance().add(dmsEvent.getMoney().amount());
        senderBalance.setClearingBalance(senderClearingBalance);
        receiverBalance.setClearingBalance(receiverClearingBalance);

        balanceRepository.saveAll(List.of(senderBalance, receiverBalance));

        Request request = getRequest(dmsEvent);
        request.setRequestStatus(RequestStatus.SUCCESS);
        request.setRequestType(RequestType.CLEARING);
        request.setOpenRequest(false);

        requestRepository.save(request);
    }

    public Balance getBalanceByNumber(String accountNumber) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found by account number "+accountNumber));

        return balanceRepository.findByAccountNumber(account)
                .orElseThrow(() -> new EntityNotFoundException("Balance not found by account number "+accountNumber));
    }

    private void validatePaymentPossible(Balance sender, BigDecimal paymentSum) {
        if (0 > sender.getAuthorizationBalance().compareTo(paymentSum)) {
            throw new IllegalArgumentException("Insufficient funds in the account");
        }
    }

    private Request getRequest(DmsEvent dmsEvent) {
        String lock = dmsEvent.getSenderId() + dmsEvent.getSenderAccountNumber() + dmsEvent.getMoney().amount();
        return requestRepository.findByLockValue(lock)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
    }
}
