package tests.discord;
import com.clown.games.mafia.Game;
import com.clown.games.mafia.GameState;
import com.clown.games.mafia.IGame;
import com.clown.games.mafia.db.Database;
import com.clown.games.mafia.db.MafiaMySQLDatabase;
import com.clown.games.mafia.db.MySQLDatabase;
import com.clown.games.mafia.discord.DiscordMafiaBot;
import com.clown.games.mafia.discord.DiscordMessageListener;
import com.clown.games.mafia.discord.DiscordMessageSender;
import com.clown.games.mafia.messaging.IMessageSender;
import com.clown.games.mafia.player.IPlayer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import tests.testClasses.TestPlayer;
import org.mockito.*;
import org.mockito.internal.util.reflection.Whitebox;
import java.util.HashMap;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class DiscordTest
{
    IGame game;
    DiscordMessageListener listener = Mockito.spy(new DiscordMessageListener());
    IMessageSender sender = Mockito.spy(new DiscordMessageSender());
    IPlayer testPlayer;
    DiscordMafiaBot discord;


    @org.junit.Before
    public void setUp()
    {
        game = Mockito.spy(new Game());
        TextChannel textChannel = Mockito.mock(TextChannel.class);
        Mockito.when(listener.getTextChannel()).thenReturn(textChannel);
        Mockito.when(textChannel.getId()).thenReturn("12");
        Whitebox.setInternalState(sender, "textChannel", textChannel);
        MafiaMySQLDatabase database = Mockito.mock(MafiaMySQLDatabase.class);
        Mockito.doNothing().when(database).connectToDb();
        discord = new DiscordMafiaBot(listener, database);
        game.setMessageSender(sender);
        testPlayer = new TestPlayer("Gordon Freeman", "id", 1);
    }

    @org.junit.Test
    public void onMessageReceived_should_helpWorkCorrectly()
    {
        HashMap<String, IGame> mockGames = new HashMap<>();
        mockGames.put("12", game);
        Whitebox.setInternalState(discord, "games",mockGames);
        Mockito.doNothing().when(sender).sendMessage("No one will help you!");
        listener.receiveMessage("!help");
    }

    @org.junit.Test
    public void onMessageReceived_should_wrongMessageWorkCorrectly()
    {
        listener.receiveMessage("wrong message");
    }

    @org.junit.Test
    public void onMessageReceived_should_mafiaStartWorkCorrectly()
    {
        HashMap<String, IGame> mockGames = new HashMap<>();
        mockGames.put("12", game);
        Whitebox.setInternalState(discord, "games",mockGames);
        Mockito.doNothing().when(game).setMessageSender(any(IMessageSender.class));
        Mockito.doNothing().when(sender).sendMessage(anyString());
        listener.receiveMessage("!Mafia");
        assert game.getCurrentGameState() == GameState.PREPARATION;
    }

    @org.junit.Test
    public void handleJoinMafia_should_workCorrectly()
    {
        HashMap<String, IGame> mockGames = new HashMap<>();
        mockGames.put("12", game);
        Whitebox.setInternalState(discord, "games",mockGames);
        Mockito.doNothing().when(game).setMessageSender(any(IMessageSender.class));
        Mockito.doNothing().when(sender).sendMessage(anyString());
        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn("name");
        Mockito.when(listener.getMessageAuthor()).thenReturn(user);
        game.prepareForGame();
        listener.receiveMessage("!join mafia");
        assert game.getCurrentPlayersCount() == 1;
    }
}
