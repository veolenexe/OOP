package com.clown.games.mafia.discord;

import com.clown.games.mafia.messaging.IMessageListener;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.util.function.Function;

public class DiscordMessageListener extends ListenerAdapter implements IMessageListener
{
    private Function<String, Boolean> receivingFunction;
    private TextChannel textChannel;
    private User messageAuthor;

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
        {
            return;
        }
        String messageContent = event.getMessage().getContentRaw();
        messageAuthor = event.getAuthor();
        String formedMessage = "P: " + messageAuthor.getId() + " " + messageContent;
        receiveMessage(formedMessage);
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
        {
            return;
        }
        String messageContent = event.getMessage().getContentRaw();

        messageAuthor = event.getAuthor();
        textChannel = event.getChannel();
        receiveMessage(messageContent);
    }

    @Override
    public void receiveMessage(String message)
    {
        receivingFunction.apply(message);
    }

    @Override
    public void setReceivingFunction(Function<String, Boolean> receivingFunction)
    {
        this.receivingFunction = receivingFunction;
    }

    TextChannel getTextChannel()
    {
        return textChannel;
    }

    User getMessageAuthor()
    {
        return messageAuthor;
    }
}
