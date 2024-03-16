package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.response.LinkResponseDto;
import edu.java.bot.service.ScrapperService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ListCommand implements Command {

    private static final String EMPTY_LINKS = "В списке нет отслеживаемых ссылок";
    private static final String COMMAND = "/list";
    private static final String COMMAND_DESCRIPTION = "Команда выводящая список всех отслеживаемых ссылок.";

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
            .getAllLinks(update.message().chat().id())
            .map(listLinkResponseDto -> new SendMessage(
                update.message().chat().id(),
                linksToString(listLinkResponseDto.links())
            ).disableWebPagePreview(true));
    }

    private String linksToString(List<LinkResponseDto> list) {
        return list.isEmpty()
            ? EMPTY_LINKS
            : list
            .stream()
            .map(this::concatLink)
            .collect(Collectors.joining("\n"));
    }

    private String concatLink(LinkResponseDto linkResponseDto) {
        if (linkResponseDto.name() != null) {
            return linkResponseDto.url() + " (" + linkResponseDto.name() + ")";
        } else {
            return linkResponseDto.url().toString();
        }
    }

}
