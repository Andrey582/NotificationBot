package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StartCommand implements Command {

    private static final String HELLO_MESSAGE = "Привет, я бот для отслеживания изменений по ссылкам,"
        + " которые ты мне скинешь. О моих возможностях ты можешь узнать используя команду /help.";
    private static final String COMMAND = "/start";
    private static final String COMMAND_DESCRIPTION = "Команда для регистрации пользователя в боте.";
    private static final String REGISTRATION_SUCCESSFUL = "Вы успешно зарегистрировались в боте.";
    private static final String REGISTRATION_FAILED = "Вы уже зарегистрированы.";

    @Autowired
    private ScrapperService scrapperService;

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
        return scrapperService
            .registerUser(update.message().chat().id())
            .map(response -> new SendMessage(
                    update.message().chat().id(),
                    getMessage(response.getStatusCode())
                )
            );
    }

    private String getMessage(HttpStatusCode code) {
        String msg;
        if (code.is2xxSuccessful()) {
            msg = REGISTRATION_SUCCESSFUL;
        } else {
            msg = REGISTRATION_FAILED;
        }
        return msg;
    }
}
