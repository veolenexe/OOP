package com.clown.games.mafia.roles.discord;

import com.clown.games.mafia.roles.Player;
import com.clown.games.mafia.roles.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Doctor extends BaseDiscordPlayer
{

    public Doctor(Player player)
    {
        super(player);
    }

    public Doctor(User user)
    {
        super(user);
    }

    public Doctor(DiscordPlayer player)
    {
        super(player);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
