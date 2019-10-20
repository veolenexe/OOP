package com.clown.games.mafia;

import com.clown.games.mafia.roles.Player;
import net.dv8tion.jda.api.entities.TextChannel;

public class DiscordMessageSender implements MessageSender
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

    @Override
    public void sendMessageToPlayer(String message, Player player)
    {

    }
}
