package me.nykon.telegram.suggestbot.config;

import me.nykon.telegram.suggestbot.props.BotProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class BotConfiguration {

    @Bean
    public @NotNull TelegramClient telegramClient(final @NotNull BotProperties properties) {
        return new OkHttpTelegramClient(properties.getToken());
    }
}
