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
import edu.java.bot.configuration.BotConfig;
import edu.java.bot.configuration.LinkValidatorConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BotApplication.class)
public class TrackCommandTest {

    @Autowired
    private MessageHandler handler;

    @Test
    public void trackCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/track https://github.com/Andrey582/NotificationBot");

        String result = handler.handleCommand(update).block().getParameters().get("text").toString();
        String expected = "Ссылка уже отслеживается или имеется ссылка с таким названием.";

        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    public void trackCommandLengthLessThan2Test() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/track");

        String result = handler.handleCommand(update).block().getParameters().get("text").toString();
        String expected = "Команда должна иметь вид \'/track link [name]\'.";

        assertThat(result)
            .isEqualTo(expected);
    }
}
