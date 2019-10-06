import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.function.Consumer;

public class Main extends ListenerAdapter
{
    public static Game game = new Game();

    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NjI3Nzg5NTkwMzI5MTYzNzc2.XZN87A.3yiiyCD52bKZ6mP0rBc8KR2Ujn4";
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {

        String messageContent = event.getMessage().getContentRaw();

        if (event.getAuthor().isBot()) return;
        if (messageContent.equals("!help"))  printHelp(event);

        if(messageContent.equals("!Mafia") || messageContent.equals("!mafia"))
        {
            game.setChannel(event.getChannel());
            game.prepareForGame();
        }

        switch (game.getCurrentGameState())
        {
            case PREPARATION:{
                switch (messageContent)
                {
                    case "!join mafia":{
                        if(!game.isPlayerParticipant(event.getAuthor()))//Убрать для теста.
                            game.addParticipant(event.getAuthor());
                        break;
                    }
                    case "!Mafia start":{
                        if (game.getCurrentPlayersCount() < 5)
                        {
                            game.getChannel().sendMessage("недостаточное кол-во игроков," +
                                    " необходимо 5 или больше людей в игре: " +
                                    game.getCurrentPlayersCount()).queue();
                        }
                        else
                            game.mafiaStart();
                        break;
                    }
                }
                break;
            }
            case DAY: {
                if (messageContent.startsWith("!vote "))
                {
                    String playerName = messageContent.substring(7);
                    String votedPlayerName = event.getAuthor().getName();
                    game.makeAVote(playerName, votedPlayerName);
                }
                break;
            }
        }
    }

    private void printHelp(GuildMessageReceivedEvent event)
    {

    }
}
