package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HelpCommand implements Command {

    private static final String COMMAND = "/help";
    private static final String COMMAND_DESCRIPTION = "Команда выводящая список всех возможных команд.";
    @Autowired
    private List<Command> availableCommands;

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public Mono<SendMessage> handle(Update update) {
        return Mono.just(
            new SendMessage(
                update.message().chat().id(),
                availableCommands.stream()
                    .map(e -> e.command() + "\n" + e.description())
                    .collect(Collectors.joining("\n\n"))
            )
        );
    }

    @Autowired
    private void addThisCommandToAvailable() {
        availableCommands.add(this);
    }
}
