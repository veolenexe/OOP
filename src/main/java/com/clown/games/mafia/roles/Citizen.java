package com.clown.games.mafia.roles;

import java.util.List;

public class Citizen extends BasePlayer
{
    public Citizen(Player player)
    {
        super(player.getPlayerName(), Role.CITIZEN);
    }

    public Citizen(String playerName)
    {
        super(playerName, Role.CITIZEN);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
