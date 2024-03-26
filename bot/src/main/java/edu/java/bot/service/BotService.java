package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.dto.request.LinkUpdateRequestDto;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BotService {

    @Autowired
    private TelegramBot telegramBot;

    public void update(LinkUpdateRequestDto linkUpdateRequestDto) {
        Map<Long, String> chatInfo = linkUpdateRequestDto.tgChatInfo();
        Flux.fromIterable(chatInfo.keySet())
            .subscribe(key -> {
                String name = chatInfo.get(key) == null ? "" : "(" + chatInfo.get(key) + ") ";

                telegramBot.execute(
                    new SendMessage(
                        key,
                        "По ссылке " + name + linkUpdateRequestDto.url() + " произошли следующие изменения:\n"
                            + linkUpdateRequestDto.description()
                    ).disableWebPagePreview(true)
                );

            });
    }
}
