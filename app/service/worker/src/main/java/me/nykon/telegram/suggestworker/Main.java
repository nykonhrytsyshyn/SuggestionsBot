package me.nykon.telegram.suggestworker;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(final @NotNull String[] args) {
        SpringApplication.run(Main.class, args);

        // Keep the main thread alive
        // This is temporary and should be replaced
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
