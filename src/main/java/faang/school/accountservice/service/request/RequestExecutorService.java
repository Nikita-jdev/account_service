package faang.school.accountservice.service.request;

import faang.school.accountservice.dto.AccountDto;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.entity.RequestTask;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RollbackStatus;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.handler.request_task.RequestTaskHandler;
import faang.school.accountservice.repository.RequestRepository;
import faang.school.accountservice.repository.RequestTaskRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestExecutorService {
    private final RequestRepository requestRepository;
    private final RequestTaskRepository requestTaskRepository;
    private final List<RequestTaskHandler> requestTaskHandlerList;
    private final RequestTaskService requestTaskService;


    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 10)
    public AccountDto executeRequest(UUID requestId, AccountDto accountDto) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found "+requestId));

        List<RequestTask> requestTasks = requestTaskService.createRequestTasks(requestId);
        for (RequestTask requestTask : requestTasks) {
            Optional<RequestTaskHandler> handlerOptional = requestTaskHandlerList.stream()
                    .filter(requestTaskHandler -> requestTaskHandler.getHandlerId().equals(requestTask.getHandler()))
                    .findFirst();
            if (handlerOptional.isPresent()) {
                RequestTaskHandler handler = handlerOptional.get();

                try {
                    handler.execute(accountDto);
                    requestTask.setRequestStatus(RequestStatus.SUCCESS);
                } catch (IllegalArgumentException e) {
                    log.error("Error executing task ");
                    try {
                        handler.rollback(accountDto);
                        requestTask.setRollbackStatus(RollbackStatus.COMPLETED);
                    } catch (Exception rollbackException) {
                        log.error("Error executing rollback ");
                        requestTask.setRollbackStatus(RollbackStatus.FAILED);
                    }
                    requestTask.setRequestStatus(RequestStatus.CANCELLED);
                } finally {
                    requestTask.setUpdatedAt(LocalDateTime.now());
                    requestTask.setCreatedAt(LocalDateTime.now());
                    requestTaskRepository.save(requestTask);
                }
            } else {
                throw new EntityNotFoundException("Handler not found");
            }
        }

        request.setRequestStatus(RequestStatus.SUCCESS);
        requestRepository.save(request);

        return accountDto;
    }
}
