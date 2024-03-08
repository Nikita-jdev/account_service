package faang.school.accountservice.service;

import faang.school.accountservice.dto.RequestDto;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.OperationStatus;
import faang.school.accountservice.mapper.RequestMapper;
import faang.school.accountservice.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Transactional
    public RequestDto createRequest(RequestDto requestDto) {
        Request createdRequest = requestMapper.toEntity(requestDto);
        createdRequest.setLockValue(1L);
        createdRequest.setOpen(true);
        createdRequest.setOperationStatus(OperationStatus.IN_PROGRESS);

        Request savedRequest = requestRepository.save(createdRequest);
        return requestMapper.toDto(savedRequest);
    }

    @Transactional(readOnly = true)
    public List<RequestDto> getAll() {
        return requestRepository.findAll().stream()
                .map(requestMapper::toDto)
                .toList();
    }

    /*@Transactional
    public Request updateRequestStatus(UUID id, String status) {
        Request request = getRequestById(id);
        request.setStatus(status);
        return request;
    }*/

    private Request getRequestById(UUID id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("request with id %s not found", id)));
    }
}
