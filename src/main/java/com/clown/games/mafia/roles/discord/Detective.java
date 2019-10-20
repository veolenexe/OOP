package com.clown.games.mafia.roles.discord;

import com.clown.games.mafia.roles.Player;
import com.clown.games.mafia.roles.Role;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Detective extends BaseDiscordPlayer
{

    public Detective(Player player)
    {
        super(player);
    }

    public Detective(User user)
    {
        super(user);
    }

    public Detective(DiscordPlayer player)
    {
        super(player);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
