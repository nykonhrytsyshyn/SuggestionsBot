package me.nykon.telegram.suggestlib.spring.actuate;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.StringUtils;

@SuppressWarnings("unused")
public abstract class SilentHealthIndicator implements HealthIndicator {

    protected static final String REASON_KEY     = "reason";
    protected static final String DEFAULT_REASON = "Health check failed";
    protected static final String DEFAULT_DETAIL = "unknown";

    private final String failedMsg;

    protected SilentHealthIndicator() {
        this.failedMsg = DEFAULT_REASON;
    }

    protected SilentHealthIndicator(final @NotNull String failedMsg) {
        this.failedMsg = StringUtils.hasText(failedMsg) ? failedMsg
                                                        : DEFAULT_REASON;
    }

    public @NotNull String getFailedMsg() {
        return this.failedMsg;
    }

    @Override
    public final @NotNull Health health() {
        final Health.Builder builder = new Health.Builder();

        try {
            this.doHealthCheck(builder);
        } catch (final Exception e) {
            builder.down();
        }

        final Health health = builder.build();

        if (
                health.getStatus() == Status.DOWN
                && !health.getDetails().containsKey(REASON_KEY)
        ) {
            return builder.withDetail(REASON_KEY, this.failedMsg)
                          .build();
        }

        return health;
    }

    protected abstract void doHealthCheck(final @NotNull Health.Builder builder) throws Exception;
}
