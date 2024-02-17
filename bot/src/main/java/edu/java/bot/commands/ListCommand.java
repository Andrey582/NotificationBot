package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.hibernate.validator.internal.engine.PredefinedScopeConfigurationImpl;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListCommand implements Command {

    private final String EMPTY_LINKS = "В списке нет отслеживаемых ссылок";
    private final String COMMAND = "/list";
    private final String COMMAND_DESCRIPTION = "Команда выводящая список всех отслеживаемых ссылок.";
    private List<String> links = new ArrayList<>();

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
            update.message().chat().id(),
            getLinks()
        );
    }

    private String getLinks() {
        return links.isEmpty()
            ? EMPTY_LINKS
            : String.join("\n", links);
    }
}
