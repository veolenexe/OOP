package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public class Citizen extends Role
{
    private static boolean madeMove = false;

    public Citizen()
    {
        role = Roles.CITIZEN;
    }

    @Override
    public IMove makeMove(IPlayer player)
    {
        return null;
    }

    @Override
    public boolean getMadeMove()
    {
        return madeMove;
    }

    @Override
    public void setMadeMove(boolean madeMove)
    {
        Citizen.madeMove = madeMove;
    }
}
