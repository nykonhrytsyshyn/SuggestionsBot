package me.nykon.telegram.suggestbot;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(final @NotNull String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
