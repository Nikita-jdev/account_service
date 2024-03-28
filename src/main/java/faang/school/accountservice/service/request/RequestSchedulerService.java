package faang.school.accountservice.service.request;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Account;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.executor.ThreadPoolExecutorHandler;
import faang.school.accountservice.mapper.AccountMapper;
import faang.school.accountservice.repository.AccountRepository;
import faang.school.accountservice.repository.RequestRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RequestSchedulerService {
    private final RequestRepository requestRepository;
    private final RequestExecutorService requestExecutorService;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final List<ThreadPoolExecutorHandler> threadPoolExecutorHandlers;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    //@PostConstruct
    public void scheduled() {
        scheduler.scheduleAtFixedRate(this::executeRequests, 0, 500, TimeUnit.MILLISECONDS);
    }

    void executeRequests() {
        List<Request> requests = requestRepository.findAll().stream()
                .filter(request -> request.getRequestStatus().equals(RequestStatus.PENDING))
                .filter(request -> request.getScheduledAt().isBefore(LocalDateTime.now())).toList();

        for (Request request : requests) {
            Optional<ThreadPoolExecutorHandler> threadPoolHandler = threadPoolExecutorHandlers.stream()
                    .filter(threadPool -> threadPool.getRequestType().equals(request.getRequestType()))
                    .findFirst();
            if (threadPoolHandler.isPresent()) {
                ThreadPoolExecutorHandler handler = threadPoolHandler.get();

                Account account = accountRepository.findByOwnerId(request.getUserId())
                        .orElseThrow(() -> new EntityNotFoundException("Account not found by Owner id"));
                AccountDto accountDto = accountMapper.toDto(account);

                handler.threadPoolExecutor().execute(() -> {
                    requestExecutorService.executeRequest(request.getId(), accountDto);
                });
            } else {
                throw new EntityNotFoundException("Handler not found");
            }
        }
    }
}
