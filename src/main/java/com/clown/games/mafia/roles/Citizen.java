package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public class Citizen extends Role
{
    public Citizen()
    {
        role = Roles.CITIZEN;
    }

    @Override
    public void makeMove(List<IPlayer> players)
    {

    }
}
