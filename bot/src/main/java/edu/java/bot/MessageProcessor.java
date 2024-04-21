package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class MessageProcessor implements UpdatesListener {

    private Counter counter;
    private TelegramBot telegramBot;
    private MessageHandler messageHandler;

    public MessageProcessor(TelegramBot telegramBot, MessageHandler messageHandler, Counter counter) {
        telegramBot.setUpdatesListener(this);

        this.telegramBot = telegramBot;
        this.messageHandler = messageHandler;
        this.counter = counter;
    }

    @Override
    public int process(List<Update> list) {

        Flux.fromIterable(list)
            .subscribe(update -> {
                if (update != null) {
                    if (update.message() != null) {
                        messageHandler.handleCommand(update).subscribe(msg -> telegramBot.execute(msg));
                        counter.increment(1.0);
                    }
                }
            });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
