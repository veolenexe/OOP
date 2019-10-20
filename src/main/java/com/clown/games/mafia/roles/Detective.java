package com.clown.games.mafia.roles;

import java.util.List;

public class Detective extends BasePlayer
{
    public Detective(Player player)
    {
        super(player.getPlayerName(), Role.DETECTIVE);
    }

    public Detective(String playerName)
    {
        super(playerName, Role.DETECTIVE);
    }

    @Override
    public void makeMove(List<Player> players)
    {

    }
}
