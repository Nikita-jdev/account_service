package faang.school.accountservice.config.redis;

import faang.school.accountservice.listener.DmsEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channels.create_request_channel.name}")
    private String createRequestChannelName;
    @Value("${spring.data.redis.channels.dms_channel.name}")
    private String dmsChannelName;
    @Value("${spring.data.redis.channels.open_account_channel.name}")
    private String openAccountChannelName;

    private final DmsEventListener dmsEventListener;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        System.out.println(port);
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    ChannelTopic createRequestTopic() {
        return new ChannelTopic(createRequestChannelName);
    }

    @Bean
    ChannelTopic dmsTopic() {
        return new ChannelTopic(dmsChannelName);
    }

    @Bean
    ChannelTopic openAccountTopic() {
        return new ChannelTopic(openAccountChannelName);
    }

    @Bean
    MessageListenerAdapter dmsListener() {
        return new MessageListenerAdapter(dmsEventListener);
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(dmsEventListener, dmsTopic());
        return container;
    }
}
