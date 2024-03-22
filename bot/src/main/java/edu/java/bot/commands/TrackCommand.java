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
public class TrackCommand implements Command {

    private static final String INCORRECT_LENGTH = "Команда должна иметь вид \'/track link [name]\'.";
    private static final String COMMAND = "/track";
    private static final String COMMAND_DESCRIPTION = "Команда добавляет ссылку для отслеживания.\n"
        + "(/track link [name]). name - необязательный параметр.";
    private static final String LINK_NOT_ADDED = "Ссылка уже отслеживается или имеется ссылка с таким названием.";
    private static final String LINK_NOT_SUPPORTED = "Ссылка не поддерживается.";
    private static final String LINK_ADD_SUCCESSFUL = " ссылка добавлена успешно.";


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
        String[] msg = update.message().text().strip().split("\s+", 3);
        if (msg.length < 2) {
            return Mono.just(INCORRECT_LENGTH);
        }
        String link = msg[1];
        URI validate = linkValidator.validate(link);
        if (validate != null) {
            return scrapperService.addLink(
                update.message().chat().id(),
                new LinkRequestDto(
                    validate,
                    msg.length > 2 ? msg[2] : null
                )
            ).map(this::getResultMsg);
        }
        return Mono.just(LINK_NOT_SUPPORTED);
    }

    private String getResultMsg(LinkResponseDto linkResponseDto) {
        if (linkResponseDto.url() != null) {
            return linkResponseDto.url() + LINK_ADD_SUCCESSFUL;
        } else {
            return LINK_NOT_ADDED;
        }
    }
}
