package faang.school.accountservice.service.request;

import faang.school.accountservice.dto.CreateRequestEvent;
import faang.school.accountservice.dto.RequestDto;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.enums.RequestType;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.mapper.RequestMapper;
import faang.school.accountservice.publisher.CreateRequestPublisher;
import faang.school.accountservice.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final CreateRequestPublisher createRequestPublisher;
    private final RequestMapper requestMapper;

    @Transactional
    public RequestDto createRequest(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);

        request.setVersion(1L);
        request.setRequestType(RequestType.CREATED);
        request.setLockValue(request.getUserId().toString() + request.getVersion().toString() + request.getId().toString());
        request.setRequestStatus(RequestStatus.SUCCESS);
        request.setOpenRequest(false);
        requestRepository.save(request);

        publishCreateRequestEventAsync(request);
        return requestMapper.toDto(request);
    }

    @Transactional
    public RequestDto getRequest(UUID requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id"));
        return requestMapper.toDto(request);
    }

    @Transactional
    public Request updateRequestStatus(UUID requestId, RequestStatus requestStatus) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id"));
        request.setRequestStatus(requestStatus);
        return requestRepository.save(request);
    }

    @Transactional
    public Request updateRequestFlag(UUID requestId, boolean flag) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
        request.setOpenRequest(flag);
        return requestRepository.save(request);
    }

    @Transactional
    public Request updateRequestContext(UUID requestId, Map<String, Object> context) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found"));
        request.setInputData(context);
        return requestRepository.save(request);
    }

    @Transactional
    @Async("taskExecutor")
    private void publishCreateRequestEventAsync(Request request) {
        createRequestPublisher.publish(new CreateRequestEvent(request.getUserId(), request.getRequestStatus(), LocalDateTime.now()));
    }
}
