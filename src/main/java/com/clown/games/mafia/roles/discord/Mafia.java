package com.clown.games.mafia.roles.discord;

import com.clown.games.mafia.roles.Player;
import com.clown.games.mafia.roles.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Mafia extends BaseDiscordPlayer
{

    public Mafia(Player player)
    {
        super(player);
    }

    public Mafia(User user)
    {
        super(user);
    }

    public Mafia(DiscordPlayer player)
    {
        super(player);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
