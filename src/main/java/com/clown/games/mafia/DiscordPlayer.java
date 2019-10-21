package com.clown.games.mafia;

import com.clown.games.mafia.roles.IPlayer;
import com.clown.games.mafia.roles.Player;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class DiscordPlayer extends Player
{
    User discordUser;

    public DiscordPlayer(User user)
    {
        super( user.getName(), user.getName()+ user.getId());
        discordUser = user;
    }

    @Override
    public void makeMove(List<IPlayer> players)
    {

    }

    @Override
    public void sendPrivateMessage(String message)
    {

    }
}
