package faang.school.accountservice.service;

import faang.school.accountservice.client.PaymentServiceClient;
import faang.school.accountservice.dto.AccountResponseDto;
import faang.school.accountservice.dto.BalanceDto;
import faang.school.accountservice.dto.PaymentDto;
import faang.school.accountservice.dto.PaymentResponseDto;
import faang.school.accountservice.dto.request.CreateRequestDto;
import faang.school.accountservice.dto.request.RequestDto;
import faang.school.accountservice.enums.PaymentStatus;
import faang.school.accountservice.exception.BalanceException;
import faang.school.accountservice.exception.CurrencyException;
import faang.school.accountservice.exception.RequestNotFoundException;
import faang.school.accountservice.model.request.Request;
import faang.school.accountservice.model.request.RequestStatus;
import faang.school.accountservice.model.request.RequestType;
import faang.school.accountservice.repository.RequestRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final BalanceService balanceService;
    private final RequestService requestService;
    private final AccountService accountService;
    private final RequestRepository requestRepository;
    private final PaymentServiceClient paymentServiceClient;

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 3, backoff = @Backoff(delay = 300))
    public void createPayment(PaymentDto paymentDto) {
        AccountResponseDto account = accountService
                .getAccountByNumber(paymentDto.getOwnerAccountNumber());
        if (account.getCurrency() != paymentDto.getCurrency()) {
            throw new CurrencyException("Wrong currency");
        }

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("receiverAccountNumber", paymentDto.getReceiverAccountNumber());
        inputData.put("ownerAccountNumber", paymentDto.getOwnerAccountNumber());
        inputData.put("currency", paymentDto.getCurrency());
        inputData.put("amount", paymentDto.getAmount());

        CreateRequestDto createRequestDto = CreateRequestDto.builder()
                .requestType(RequestType.TRANSFER_FUNDS)
                .idempotencyKey(paymentDto.getIdempotencyKey())
                .ownerType(account.getOwnerType())
                .ownerId(account.getOwnerId())
                .inputData(inputData)
                .build();

        RequestDto requestDto = requestService.getOrSave(createRequestDto);
        Request request = requestRepository.findById(requestDto.getId())
                .orElseThrow(() -> new RequestNotFoundException("Request not found"));
        if (request.getRequestStatus() == RequestStatus.TO_DO) {
            authoriseBalance(paymentDto.getAmount(), request);
            requestService.updateRequestStatus(request.getId(), RequestStatus.IN_PROGRESS, null);
        }
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 3, backoff = @Backoff(delay = 300))
    public void clearPayment(UUID idempotencyKey) {
        log.info("Payment with UUID = {} start clearing", idempotencyKey);
        Request request = requestRepository.findByIdempotencyKey(idempotencyKey)
                .orElseThrow(() -> new RequestNotFoundException("Request not found"));
        if (request.getRequestStatus() == RequestStatus.IN_PROGRESS) {
            updateActualBalance(request);
            requestService.updateRequestStatus(request.getId(), RequestStatus.DONE, null);
        }
        if (request.getRequestStatus() == RequestStatus.DONE) {
            paymentServiceClient.sendAnswer(PaymentResponseDto.builder()
                    .status(PaymentStatus.SUCCESS)
                    .idempotencyKey(request.getIdempotencyKey())
                    .build());
        }
        log.info("Payment with UUID = {} was cleared", idempotencyKey);
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 3, backoff = @Backoff(delay = 300))
    public void refundPayment(UUID idempotencyKey) {
        Request request = requestRepository.findByIdempotencyKey(idempotencyKey)
                .orElseThrow(() -> new RequestNotFoundException("Request not found"));
        if (request.getRequestStatus() == RequestStatus.IN_PROGRESS) {
            BigDecimal amount = BigDecimal.valueOf ((Double) request.getInputData().get("amount"));
            BalanceDto ownerBalance = getBalance(request, "ownerAccountNumber");
            BalanceDto receiverBalance = getBalance(request, "receiverAccountNumber");

            ownerBalance.setCurrentAuthorizationBalance(ownerBalance.getCurrentAuthorizationBalance().add(amount));
            receiverBalance.setCurrentAuthorizationBalance(receiverBalance.getCurrentAuthorizationBalance().subtract(amount));

            balanceService.update(ownerBalance);
            balanceService.update(receiverBalance);
            requestService.updateRequestStatus(request.getId(), RequestStatus.DONE, null);
        }
        if (request.getRequestStatus() == RequestStatus.DONE) {
            paymentServiceClient.sendAnswer(PaymentResponseDto.builder()
                    .status(PaymentStatus.REFUND)
                    .idempotencyKey(request.getIdempotencyKey())
                    .build());
        }
    }

    private void authoriseBalance(BigDecimal amount, Request request) {
        BalanceDto receiverBalance = getBalance(request, "receiverAccountNumber");
        BalanceDto ownerBalance = getBalance(request, "ownerAccountNumber");
        BigDecimal currentOwnerAuthorizationBalance = ownerBalance.getCurrentAuthorizationBalance();
        if (currentOwnerAuthorizationBalance.compareTo(amount) < 0) {
            requestService.updateRequestStatus(request.getId(), RequestStatus.FAILURE, "Not enough money");
                throw new BalanceException("Not enough money");
        }
        ownerBalance.setCurrentAuthorizationBalance(currentOwnerAuthorizationBalance.subtract(amount));
        receiverBalance.setCurrentAuthorizationBalance(receiverBalance.getCurrentAuthorizationBalance().add(amount));
        balanceService.update(ownerBalance);
        balanceService.update(receiverBalance);
    }

    private void updateActualBalance(Request request) {
        BigDecimal amount = BigDecimal.valueOf ((Double) request.getInputData().get("amount"));

        BalanceDto receiverBalance = getBalance(request, "receiverAccountNumber");
        BalanceDto ownerBalance = getBalance(request, "ownerAccountNumber");

        ownerBalance.setCurrentActualBalance(ownerBalance.getCurrentActualBalance().subtract(amount));
        receiverBalance.setCurrentActualBalance(receiverBalance.getCurrentActualBalance().add(amount));

        balanceService.update(receiverBalance);
        balanceService.update(ownerBalance);
    }

    private BalanceDto getBalance(Request request, String accountNumberName) {
        String accountNumber = String.valueOf(request.getInputData().get(accountNumberName));
        AccountResponseDto account = accountService.getAccountByNumber(accountNumber);
        return balanceService.getBalance(account.getId());
    }
}