package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public class Detective extends Role
{
    public Detective()
    {
        role = Roles.DETECTIVE;
    }

    @Override
    public Move makeMove(List<IPlayer> players)
    {

        return null;
    }
}
