package me.nykon.telegram.suggestbot.props;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "bot")
public final class BotProperties {

    private final String username;
    private final String token;

    @ConstructorBinding
    public BotProperties(
            final String username,
            final String token
    ) {
        Preconditions.checkArgument(!StringUtils.isBlank(username), "Bot username cannot be null or blank");
        Preconditions.checkArgument(!StringUtils.isBlank(token), "Bot token cannot be null or blank");

        this.username = username;
        this.token = token;
    }

    public @NotNull String getUsername() {
        return this.username;
    }

    public @NotNull String getToken() {
        return this.token;
    }
}
