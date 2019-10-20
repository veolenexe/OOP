package com.clown.games.mafia.roles.discord;

import com.clown.games.mafia.roles.Player;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Citizen extends BaseDiscordPlayer
{
    public Citizen(Player player)
    {
        super(player);
    }

    public Citizen(User user)
    {
        super(user);
    }

    public Citizen(DiscordPlayer player)
    {
        super(player);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
