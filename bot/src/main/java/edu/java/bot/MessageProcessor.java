package edu.java.bot;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

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
