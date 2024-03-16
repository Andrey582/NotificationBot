package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import java.util.List;
import edu.java.bot.service.ScrapperService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BotApplication.class)
public class StartCommandTest {

    @Autowired
    MessageHandler handler;

    @Test
    public void startCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/start");
        ScrapperService scrapperService = Mockito.mock(ScrapperService.class);
        Mockito.when(scrapperService.registerUser(1L)).thenReturn(Mono.just(ResponseEntity.ok().build()));

        String result = handler.handleCommand(update).block().getParameters().get("text").toString();
        String expected = "Вы уже зарегистрированы.";

        assertThat(result)
            .isEqualTo(expected);
    }
}
