package com.clown.games.mafia.roles;

import java.util.List;

public class Doctor extends BasePlayer
{
    public Doctor(Player player)
    {
        super(player.getPlayerName(), Role.DOCTOR);
    }

    public Doctor(String playerName)
    {
        super(playerName, Role.DOCTOR);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
