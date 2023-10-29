package faang.school.accountservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.async")
public class AsyncConfig {
    @Value("${spring.async.corePoolSize}")
    private int corePoolSize;
    @Value("${spring.async.maxPoolSize}")
    private int maxPoolSize;
    @Value("${spring.async.queueCapacity}")
    private int queueCapacity;
    @Value("${spring.async.threadNamePrefix}")
    private String threadNamePrefix;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}