package me.nykon.telegram.suggestlib.spring.actuate;

import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

import javax.annotation.CheckForNull;
import java.util.Objects;

public class RabbitHealthIndicator extends SilentHealthIndicator {

    private static final String VERSION_KEY = "version";

    private final RabbitTemplate template;

    public RabbitHealthIndicator(final @CheckForNull RabbitTemplate template) {
        super("Could not connect to RabbitMQ");

        Assert.notNull(template, "Rabbit template cannot be null");

        this.template = template;
    }

    @Override
    protected void doHealthCheck(final @NotNull Health.Builder builder) {
        final String version = this.template.execute(channel ->
                channel.getConnection()
                       .getServerProperties()
                       .get(VERSION_KEY)
                       .toString()
        );

        builder.up()
               .withDetail(VERSION_KEY, Objects.requireNonNullElse(version, DEFAULT_DETAIL));
    }
}
