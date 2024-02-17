package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    private final String HELLO_MESSAGE = "Привет, я бот для отслеживания изменений по ссылкам," +
        " которые ты мне скинешь. О моих возможностях ты можешь узнать используя команду /help.";
    private final String COMMAND = "/start";
    private final String COMMAND_DESCRIPTION = "Команда для регистрации пользователя в боте.";

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
            HELLO_MESSAGE);
    }
}
