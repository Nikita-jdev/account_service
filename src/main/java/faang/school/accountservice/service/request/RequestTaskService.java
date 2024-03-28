package faang.school.accountservice.service.request;

import faang.school.accountservice.entity.RequestTask;
import faang.school.accountservice.enums.RequestStatus;
import faang.school.accountservice.repository.RequestTaskRepository;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestTaskService {
    private final RequestTaskRepository requestTaskRepository;

    public List<RequestTask> createRequestTasks(UUID requestId) {
        if (!requestTaskRepository.findById(requestId).equals(Optional.empty())) {
            throw new IllegalArgumentException("There are already have request task");
        }

        RequestTask requestTaskForAccountLimit = RequestTask.builder()
                .handler("check account limit")
                .requestStatus(RequestStatus.TO_DO)
                .requestId(requestId)
                .version(1L)
                .build();
        RequestTask requestTaskForCreateAccount = RequestTask.builder()
                .handler("create account")
                .requestStatus(RequestStatus.TO_DO)
                .requestId(requestId)
                .version(1L)
                .build();
        RequestTask requestTaskForCreateBalance = RequestTask.builder()
                .handler("create balance")
                .requestStatus(RequestStatus.TO_DO)
                .requestId(requestId)
                .version(1L)
                .build();
        RequestTask requestTaskForSendNotification = RequestTask.builder()
                .handler("send notification")
                .requestStatus(RequestStatus.TO_DO)
                .requestId(requestId)
                .version(1L)
                .build();

        return List.of(requestTaskForAccountLimit, requestTaskForCreateAccount, requestTaskForCreateBalance, requestTaskForSendNotification);
    }

}
