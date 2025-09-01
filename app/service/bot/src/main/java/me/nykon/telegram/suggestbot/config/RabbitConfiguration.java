package me.nykon.telegram.suggestbot.config;

import me.nykon.telegram.suggestlib.spring.actuate.RabbitHealthIndicator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.CheckForNull;

@Configuration
public class RabbitConfiguration {

    @Bean
    public RabbitHealthIndicator rabbitHealthIndicator(final @CheckForNull RabbitTemplate template) {
        return new RabbitHealthIndicator(template);
    }
}
