package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

    private static final String NOT_FOUND_COMMAND = "Команда не найдена. Используте /help.";
    @Autowired
    private List<Command> commands;

    public SendMessage handleCommand(Update update) {
        Optional<Command> command = commands.stream()
            .filter(e -> e.supports(update))
            .findFirst();

        return command.isPresent()
            ? command.get().handle(update)
            : new SendMessage(
                update.message().chat().id(),
                NOT_FOUND_COMMAND
        );
    }
}
