package faang.school.accountservice.executor;

import faang.school.accountservice.enums.RequestType;

import java.util.concurrent.ThreadPoolExecutor;

public interface ThreadPoolExecutorHandler {
    RequestType getRequestType();

    ThreadPoolExecutor threadPoolExecutor();
}
