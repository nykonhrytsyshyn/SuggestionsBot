package me.nykon.telegram.suggestbot;

import me.nykon.telegram.suggestbot.props.BotProperties;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@SuppressWarnings("unused")
@Component
public final class SuggestionsBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final BotProperties properties;
    private final TelegramClient client;

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionsBot.class);

    public SuggestionsBot(
            final @NotNull BotProperties properties,
            final @NotNull TelegramClient client
    ) {
        this.properties = properties;
        this.client = client;
    }

    public @NotNull BotProperties getProperties() {
        return this.properties;
    }

    @Override
    public @NotNull String getBotToken() {
        return this.properties.getToken();
    }

    @Override
    public @NotNull LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(final @NotNull Update update) {
        final Message msg = update.getMessage();

        if (
                msg != null
                && msg.hasText()
                && msg.isUserMessage()
        ) {
            try {
                this.client.execute(
                        SendMessage
                        .builder()
                        .chatId(msg.getChatId())
                        .replyToMessageId(msg.getMessageId())
                        .text(msg.getText())
                        .build()
                );
            } catch (final TelegramApiException e) {
                LOGGER.error("Error occurred: ", e);
            }
        }
    }
}
