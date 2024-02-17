package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import org.apache.kafka.common.network.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class MessageHandler {

    private final String NOT_FOUND_COMMAND = "Команда не найдена. Используте /help.";
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
