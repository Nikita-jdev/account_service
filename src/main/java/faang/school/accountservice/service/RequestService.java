package faang.school.accountservice.service;

import faang.school.accountservice.dto.RequestDto;
import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.OperationStatus;
import faang.school.accountservice.mapper.RequestMapper;
import faang.school.accountservice.repository.RequestRepository;
import faang.school.accountservice.validator.RequestValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final RequestValidator requestValidator;
    private final RequestMapper requestMapper;

    @Transactional
    public RequestDto createRequest(RequestDto requestDto) {
        Request buildRequest = buildRequestToCreate(requestDto);
        Request savedRequest = requestRepository.save(buildRequest);

        log.info("Request with token = {} and userId = {} saved", savedRequest.getToken(), savedRequest.getUserId());
        return requestMapper.toDto(savedRequest);
    }

    private Request buildRequestToCreate(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);
        request.setOpen(false);
        request.setOperationStatus(OperationStatus.PENDING);
        request.setLockValue(1L);///////////
        return request;
    }

    public RequestDto updateRequest(RequestDto requestDto) {
        requestValidator.validateAccess(requestDto);

        Request request = getRequestByToken(requestDto.getToken());

        request.setInputData(requestDto.getInputData());
        request.setStatusDetails(requestDto.getStatusDetails());

        requestRepository.save(request);
        log.info("Request with token = {}, userId = {} updated", request.getToken(), request.getUserId());
        return requestMapper.toDto(request);
    }

    @Transactional(readOnly = true)
    public List<RequestDto> getAll() {
        return requestRepository.findAll().stream()
                .map(requestMapper::toDto)
                .toList();
    }

    @Transactional
    @Async
    public void executeRequests() {
        executeRequestsPartitions();
    }

    private void executeRequestsPartitions () {
        System.out.println("some action to requests");

        List<Request> requests = requestRepository.findAll();
        List<List<Request>> partitions = ListUtils.partition(requests, 5);

        partitions.forEach(System.out::println);
    }

    private Request getRequestByToken(UUID id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Request with id %s not found", id)));
    }
}
