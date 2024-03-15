package faang.school.accountservice.service;

import faang.school.accountservice.dto.CreateRequestEvent;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.publisher.CreateRequestPublisher;
import faang.school.accountservice.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final CreateRequestPublisher createRequestPublisher;

    public Request createRequest(Request request) {
        request.setRequestStatus(RequestStatus.SUCCESS);
        requestRepository.save(request);
        publishCreateRequestEventAsync(request);
        return request;
    }

    public Request updateRequestStatus(UUID requestId, RequestStatus requestStatus) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id"));
        request.setRequestStatus(requestStatus);
        return requestRepository.save(request);
    }

    public Request updateRequestFlag(UUID requestId, boolean flag) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
        request.setOpenRequest(flag);
        return requestRepository.save(request);
    }

    public Request updateRequestContext(UUID requestId, Map<String, Object> context) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
        request.setInputData(context);
        return requestRepository.save(request);
    }

    @Async("taskExecutor")
    private void publishCreateRequestEventAsync(Request request) {
        createRequestPublisher.publish(new CreateRequestEvent(request.getUserId(), request.getRequestStatus(), LocalDateTime.now()));
    }
}
