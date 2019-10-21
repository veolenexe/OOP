package com.clown.games.mafia;

import com.clown.games.mafia.roles.IPlayer;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class DiscordMessageSender implements IMessageSender
{
    private TextChannel textChannel;
    private DiscordMafiaBot discordMafiaBot;

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
    public void sendMessageToPlayer(String message, IPlayer player)
    {
        User user = discordMafiaBot.getUserByPlayer(player);
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }

    void setDiscordMafiaBot(DiscordMafiaBot discordMafiaBot)
    {
        this.discordMafiaBot = discordMafiaBot;
    }
}
