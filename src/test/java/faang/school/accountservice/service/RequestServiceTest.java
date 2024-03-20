package faang.school.accountservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import faang.school.accountservice.entity.Request;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.exception.EntityNotFoundException;
import faang.school.accountservice.publisher.CreateRequestPublisher;
import faang.school.accountservice.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @InjectMocks
    private RequestService requestService;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private CreateRequestPublisher createRequestPublisher;

    private Request request;
    UUID uuid = new UUID(1L, 2L);

    @BeforeEach
    void setUp() {
        request = Request.builder()
                .userId(1L)
                .lockValue("1245321")
                .isOpenRequest(true)
                .requestStatus(RequestStatus.PENDING)
                .build();
    }

    @Test
    void testCreateRequest() {
        requestService.createRequest(request);
        verify(requestRepository, times(1)).save(request);
        assertEquals(RequestStatus.SUCCESS, request.getRequestStatus());
    }

    @Test
    void testRequestNotFound() {
        when(requestRepository.findById(uuid)).thenReturn(Optional.empty());
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
                () -> requestService.updateRequestStatus(uuid, RequestStatus.PENDING));
        assertEquals("Request not found by id", e.getMessage());
    }

    @Test
    void testIsStatusUpdated() {
        when(requestRepository.findById(uuid)).thenReturn(Optional.ofNullable(request));
        requestService.updateRequestStatus(uuid, RequestStatus.PENDING);
        assertEquals(RequestStatus.PENDING, request.getRequestStatus());
    }

    @Test
    void testIsFlagUpdated() {
        when(requestRepository.findById(uuid)).thenReturn(Optional.ofNullable(request));
        requestService.updateRequestFlag(uuid, true);
        assertTrue(request.isOpenRequest());
    }
}
