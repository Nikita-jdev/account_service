package faang.school.accountservice.scheduler;


import faang.school.accountservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestScheduler {
    private final RequestService requestService;

    @Scheduled(cron = "${scheduler.cron.request}")
    public void executeRequest() {
        requestService.executeRequests();
    }
}
