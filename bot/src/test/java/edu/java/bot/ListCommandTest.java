package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ListCommandTest {

    @Test
    public void listCommandTest() {
        MessageHandler handler = new MessageHandler();
        ReflectionTestUtils.setField(
            handler,
            "commands",
            List.of(
                new StartCommand(),
                new HelpCommand(),
                new TrackCommand(),
                new UntrackCommand(),
                new ListCommand()
            )
        );
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/list");

        String result = handler.handleCommand(update).getParameters().get("text").toString();
        String expected = "В списке нет отслеживаемых ссылок";

        assertThat(result)
            .isEqualTo(expected);
    }
}
