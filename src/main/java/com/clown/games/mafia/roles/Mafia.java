package com.clown.games.mafia.roles;

import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Mafia extends BasePlayer
{
    public Mafia(Player player)
    {
        super(player.getPlayerName(), Role.MAFIA);
    }

    public Mafia(String playerName)
    {
        super(playerName, Role.MAFIA);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
