package me.nykon.telegram.suggestbot;

import me.nykon.telegram.suggestbot.props.BotProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(BotProperties.class)
@SpringBootApplication
public class Main {

    public static void main(final @NotNull String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
