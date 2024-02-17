package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor implements UpdatesListener {

    private TelegramBot telegramBot;
    private MessageHandler messageHandler;

    public MessageProcessor(TelegramBot telegramBot, MessageHandler messageHandler) {
        telegramBot.setUpdatesListener(this);

        this.telegramBot = telegramBot;
        this.messageHandler = messageHandler;
    }

    @Override
    public int process(List<Update> list) {
        if (!list.isEmpty()) {
            list.forEach(update -> {
                if (update != null) {
                    SendMessage msg = messageHandler.handleCommand(update);

                    telegramBot.execute(msg);
                }
            });
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
