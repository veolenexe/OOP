package com.clown.games.mafia.discord;

import com.clown.games.mafia.messaging.IMessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class DiscordPrivateMessageListener extends PrivateMessageReceivedEvent implements IMessageListener
{
    private Function<String, Boolean> receivingFunction;
    private TextChannel textChannel;
    private User messageAuthor;

    public DiscordPrivateMessageListener(@Nonnull JDA api, long responseNumber, @Nonnull Message message)
    {
        super(api, responseNumber, message);
    }


    @Override
    public void receiveMessage(String message)
    {
        System.out.println(message);
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
