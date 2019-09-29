import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter
{
    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NjI3Nzg5NTkwMzI5MTYzNzc2.XZDg6A.VdwEo5XIJyn0xqjs-4ZmE3dgIP0";
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().equals("!help"))
            printHelp(event);
        if(event.getMessage().getContentRaw().equals("!Mafia") || event.getMessage().getContentRaw().equals("!mafia"))
        {
            TextChannel mafiaChannel = event.getChannel();

            event.getAuthor().openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage("Привет, го приват? ;)").queue();
            });
        }

    }

    private void printHelp(GuildMessageReceivedEvent event)
    {

    }
}
