package com.clown.games.mafia.discord;

import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.player.Player;
import com.clown.games.mafia.roles.Citizen;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class DiscordPlayer extends Player
{
    private User discordUser;

    DiscordPlayer(User user, int playerNumber)
    {
        super(user.getName(), user.getId(), playerNumber);
        discordUser = user;
    }

    @Override
    public void sendPrivateMessage(String message)
    {
        discordUser.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }

    @Override
    public void makeMove(List<IPlayer> players)
    {
        super.makeMove(players);
    }
}
