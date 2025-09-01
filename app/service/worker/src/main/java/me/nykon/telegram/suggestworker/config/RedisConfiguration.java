package me.nykon.telegram.suggestworker.config;

import me.nykon.telegram.suggestlib.spring.actuate.RedisHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.CheckForNull;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisHealthIndicator redisHealthIndicator(final @CheckForNull RedisConnectionFactory factory) {
        return new RedisHealthIndicator(factory);
    }
}
