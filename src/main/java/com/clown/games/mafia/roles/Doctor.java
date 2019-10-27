package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

public class Doctor extends Role
{
    private static boolean madeMove = false;

    Doctor()
    {
        playerMovePriority = 2;
        role = Roles.DOCTOR;
    }

    @Override
    public IMove makeMove(IPlayer player)
    {
        return () -> player.setIsDead(false);
    }

    @Override
    public boolean getMadeMove()
    {
        return madeMove;
    }

    @Override
    public void setMadeMove(boolean madeMove)
    {
        Doctor.madeMove = madeMove;
    }
}
