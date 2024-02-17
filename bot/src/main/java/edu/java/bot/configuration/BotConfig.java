package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.commands.Command;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfig {

    @Autowired
    private List<Command> commands;

    @Bean
    public TelegramBot bot(ApplicationConfig applicationConfig) {
        TelegramBot telegramBot = new TelegramBot(applicationConfig.telegramToken());
        createMenu(telegramBot);
        return telegramBot;
    }

    private void createMenu(TelegramBot telegramBot) {
        telegramBot.execute(
            new SetMyCommands(
                commands.stream()
                    .map(Command::toApiCommand)
                    .collect(Collectors.toList())
                    .toArray(BotCommand[]::new)
            )
        );
    }
}
