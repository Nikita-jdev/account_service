package faang.school.accountservice.service;

import faang.school.accountservice.config.converter.ObjectToMapConverter;
import faang.school.accountservice.dto.RequestTemplate;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.mapper.RequestMapper;
import faang.school.accountservice.repository.RequestRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Transactional
    public boolean createRequest(String edempotyToken, RequestTemplate requestTemplate) {
        if (requestRepository.existsByToken(edempotyToken)) {
            return false;
        } else {
            Request request = requestMapper.toEntity(requestTemplate);
            requestRepository.save(request);
            return true;
        }
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class)
    public void updateRequestStatus(long requestId, RequestStatus requestStatus, @Nullable String statusDetails) {
        Request request = getRequestById(requestId);
        request.setRequestStatus(requestStatus);
        if (statusDetails != null) {
            request.setStatusDetails(statusDetails);
        }
        requestRepository.save(request);
    }

    @Transactional
    public void updateRequestFlag(long requestId, boolean isOpen) {
        Request request = getRequestById(requestId);
        request.setOpen(isOpen);
        requestRepository.save(request);
    }

    @Transactional
    public void updateRequestContext(long requestId, Object requestData) {
        Request request = getRequestById(requestId);
        request.setRequestData(ObjectToMapConverter.convertObjectToMap(requestData));
        requestRepository.save(request);
    }

    private Request getRequestById(long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }
}
