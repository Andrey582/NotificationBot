package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.request.LinkRequestDto;
import edu.java.bot.dto.response.LinkResponseDto;
import edu.java.bot.service.ScrapperService;
import edu.java.bot.validate.LinkValidator;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UntrackCommand implements Command {

    private static final String INCORRECT_LENGTH = "Команда должна иметь вид \'/untrack link | name\'.";
    private static final String COMMAND = "/untrack";
    private static final String COMMAND_DESCRIPTION = "Команда удаляет отслеживаемую ссылку.\n"
        + "(/untrack link | name). Используте ссылку или ее название.";
    private static final String LINK_NOT_FOUND = "Не удалось найти ссылку.";
    private static final String LINK_DELETE_SUCCESSFUL = " ссылка удалена успешно.";
    @Autowired
    private LinkValidator linkValidator;

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
        return validateLink(update)
            .map(msg -> new SendMessage(
                update.message().chat().id(),
                msg
            ).disableWebPagePreview(true));
    }

    @SuppressWarnings("MagicNumber")
    private Mono<String> validateLink(Update update) {
        String[] msg = update.message().text().strip().split("\s+", 2);
        if (msg.length < 2) {
            return Mono.just(INCORRECT_LENGTH);
        }
        String link = msg[1];
        URI validate = linkValidator.validate(link);
        return scrapperService
            .deleteLink(
                update.message().chat().id(),
                new LinkRequestDto(
                    validate,
                    validate != null ? null : msg[1]
                )
            ).map(this::getResultMsg);
    }

    private String getResultMsg(LinkResponseDto linkResponseDto) {
        if (linkResponseDto.url() != null || linkResponseDto.name() != null) {
            return linkResponseDto.url() + LINK_DELETE_SUCCESSFUL;
        } else {
            return LINK_NOT_FOUND;
        }
    }
}
