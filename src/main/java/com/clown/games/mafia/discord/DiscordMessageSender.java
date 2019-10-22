package com.clown.games.mafia.discord;

import com.clown.games.mafia.messaging.IMessageSender;
import com.clown.games.mafia.player.IPlayer;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscordMessageSender implements IMessageSender
{
    private TextChannel textChannel;

    void setTextChannel(TextChannel textChannel)
    {
        this.textChannel = textChannel;
    }

    @Override
    public void sendMessage(String message)
    {
        textChannel.sendMessage(message).queue();
    }
}
