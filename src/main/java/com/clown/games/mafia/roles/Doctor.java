package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public class Doctor extends Role
{
    private static boolean madeMove = false;

    public Doctor()
    {
        role = Roles.DOCTOR;
    }

    @Override
    public IMove makeMove(IPlayer player)
    {
        return () -> player.sendPrivateMessage("ТЫ ОЖИЛ");
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
