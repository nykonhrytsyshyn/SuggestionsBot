package me.nykon.telegram.suggestbot.props;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@ConfigurationProperties("bot")
public final class BotProperties {

    private final String username;
    private final transient String token;

    @ConstructorBinding
    public BotProperties(
            final String username,
            final String token
    ) {
        Assert.isTrue(StringUtils.hasText(username), "Bot username cannot be null or blank");
        Assert.isTrue(StringUtils.hasText(token),    "Bot token cannot be null or blank");

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
