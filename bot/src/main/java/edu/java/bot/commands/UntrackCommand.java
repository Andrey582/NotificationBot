package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.validate.LinkValidator;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    private static final String INCORRECT_LENGTH = "Команда должна иметь вид \'/untrack link | name\'.";
    private static final String COMMAND = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Команда удаляет отслеживаемую ссылку.\n"
        + "(/untrack link | name). Используте ссылку или ее название.";

    @Autowired
    private LinkValidator linkValidator;

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
        URI validate = linkValidator.validate(link);
        return validate != null ? link + " была удалена." : "Ссылка не поддерживается.";
    }
}
