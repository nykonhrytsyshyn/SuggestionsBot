package me.nykon.telegram.suggestlib.spring.actuate;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.redis.connection.ClusterInfo;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.Assert;

import javax.annotation.CheckForNull;
import java.util.Objects;
import java.util.Properties;

public class RedisHealthIndicator extends SilentHealthIndicator {

    private static final String VERSION_KEY                = "version";
    private static final String CLUSTER_SIZE_KEY           = "cluster_size";
    private static final String CLUSTER_SLOTS_UP_KEY       = "cluster_slots_up";
    private static final String CLUSTER_SLOTS_FAIL_KEY     = "cluster_slots_fail";
    private static final String REDIS_VERSION_PROPERTY_KEY = "redis_version";
    private static final String CLUSTER_FAIL_STATE         = "fail";

    private final RedisConnectionFactory factory;

    public RedisHealthIndicator(final @CheckForNull RedisConnectionFactory factory) {
        super("Could not connect to Redis");

        Assert.notNull(factory, "Redis connection factory cannot be null");

        this.factory = factory;
    }

    @Override
    public void doHealthCheck(final @NotNull Health.Builder builder) {
        try (final var connection = this.factory.getConnection()) {
            if (connection instanceof final RedisClusterConnection clusterConnection) {
                checkCluster(clusterConnection, builder);
            } else {
                checkRedis(connection, builder);
            }
        }
    }

    private static void checkRedis(
            final @NotNull RedisConnection connection,
            final @NotNull Health.Builder builder
    ) {
        final Properties redisProps = connection.serverCommands().info();
        final String version = redisProps != null
                               ? redisProps.getProperty(REDIS_VERSION_PROPERTY_KEY)
                               : null;

        builder.up()
               .withDetail(VERSION_KEY, Objects.requireNonNullElse(version, DEFAULT_DETAIL));
    }

    private static void checkCluster(
            final @NotNull RedisClusterConnection connection,
            final @NotNull Health.Builder builder
    ) {
        final ClusterInfo clusterInfo = connection.clusterGetClusterInfo();

        builder.withDetail(CLUSTER_SIZE_KEY,       Objects.requireNonNullElse(clusterInfo.getClusterSize(), DEFAULT_DETAIL))
               .withDetail(CLUSTER_SLOTS_UP_KEY,   Objects.requireNonNullElse(clusterInfo.getSlotsOk(),     DEFAULT_DETAIL))
               .withDetail(CLUSTER_SLOTS_FAIL_KEY, Objects.requireNonNullElse(clusterInfo.getSlotsFail(),   DEFAULT_DETAIL));

        if (CLUSTER_FAIL_STATE.equalsIgnoreCase(clusterInfo.getState())) {
            builder.down()
                   .withDetail(REASON_KEY, "Redis cluster is in fail state");
        } else {
            builder.up();
        }
    }
}
