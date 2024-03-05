package faang.school.accountservice.service;

import faang.school.accountservice.config.converter.ObjectToMapConverter;
import faang.school.accountservice.dto.RequestTemplate;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.repository.RequestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;

    @Transactional
    public boolean createRequest(String edempotyToken, RequestTemplate requestTemplate) {
        if (requestRepository.existsByToken(edempotyToken)) {
            return false;
        }else {
            Request request = new Request();
            request.setToken(edempotyToken);
            request.setOwnerId(requestTemplate.getOwnerId());
            request.setOwnerType(requestTemplate.getOwnerType());
            request.setRequestType(requestTemplate.getRequestType());
            request.setLockValue(requestTemplate.getLockValue());
            request.setRequestData(ObjectToMapConverter.convertObjectToMap(requestTemplate.getRequestData()));
            request.setRequestStatus(requestTemplate.getRequestStatus());
            request.setStatusDetails(requestTemplate.getStatusDetails());
            requestRepository.save(request);
            return true;
        }
    }

    @Transactional
    @Lock(LockModeType.OPTIMISTIC)
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
