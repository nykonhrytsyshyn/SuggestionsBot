package me.nykon.telegram.suggestbot;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationPropertiesScan("me.nykon.telegram.suggestbot.props")
@SpringBootApplication
public class Main {

    public static void main(final @NotNull String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
