package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import reactor.core.publisher.Mono;

public interface Command {

    String command();

    String description();

    Mono<SendMessage> handle(Update update);

    @SuppressWarnings("MageicNumber")
    default boolean supports(Update update) {
        if (update.message() == null) {
            return false;
        } else if (update.message().text() == null) {
            return false;
        } else {
            return update
                .message()
                .text()
                .strip()
                .split(" ", 2)[0]
                .equals(command());
        }
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
