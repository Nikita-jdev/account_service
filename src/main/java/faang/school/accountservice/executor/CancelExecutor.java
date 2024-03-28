package faang.school.accountservice.executor;

import faang.school.accountservice.enums.RequestType;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class CancelExecutor implements ThreadPoolExecutorHandler{

    @Override
    public RequestType getRequestType() {
        return RequestType.CANCEL;
    }

    @Override
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(4, 8, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000));
    }
}
