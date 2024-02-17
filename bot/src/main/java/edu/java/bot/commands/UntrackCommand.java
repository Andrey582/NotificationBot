package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    private final String INCORRECT_LENGTH = "Команда должна иметь вид \'/untrack link\'.";
    private final String COMMAND = "/untrack";
    private final String COMMAND_DESCRIPTION = "Команда удаляет отслеживаемую ссылку." +
        " (/untrack link)";

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
            getLink(update)
        );
    }

    private String getLink(Update update) {
        String[] msg = update.message().text().split(" ");
        if (msg.length < 2) {
            return INCORRECT_LENGTH;
        }
        String link = msg[1];
        return link + " была удалена.";
    }
}
