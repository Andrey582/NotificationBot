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

public class HelpCommandTest {

    @Test
    public void helpCommandTest() {
        HelpCommand command = new HelpCommand();
        ReflectionTestUtils.setField(
            command,
            "availableCommands",
            List.of(
                new ListCommand(),
                new StartCommand(),
                new TrackCommand(),
                new UntrackCommand()
            )
        );
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);
        Mockito.when(message.text()).thenReturn("/help");

        String result = command.handle(update).getParameters().get("text").toString();
        String expected = "/list\n" +
            "Команда выводящая список всех отслеживаемых ссылок.\n\n" +
            "/start\n" +
            "Команда для регистрации пользователя в боте.\n\n" +
            "/track\n" +
            "Команда добавляет ссылку для отслеживания. (/track link)\n\n" +
            "/untrack\n" +
            "Команда удаляет отслеживаемую ссылку. (/untrack link)";

        assertThat(result)
            .isEqualTo(expected);
    }
}
