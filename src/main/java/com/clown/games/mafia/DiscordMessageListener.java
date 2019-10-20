package com.clown.games.mafia;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.function.Function;

public class DiscordMessageListener extends ListenerAdapter implements MessageListener
{
    private Function<String, Boolean> receivingFunction;
    private TextChannel textChannel;
    private User messageAuthor;
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
        {
            return;
        }
        String messageContent = event.getMessage().getContentRaw();
        messageAuthor = event.getAuthor();
        if ("!Mafia".equalsIgnoreCase(messageContent))
        {
            textChannel = event.getChannel();
        }
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
