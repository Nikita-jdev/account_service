package faang.school.accountservice.executor;

import faang.school.accountservice.enums.RequestType;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AuthorizationExecutor implements ThreadPoolExecutorHandler{

    @Override
    public RequestType getRequestType() {
        return RequestType.AUTHORIZATION;
    }

    @Override
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(8, 16, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000));
    }
}
