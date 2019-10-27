package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public class Detective extends Role
{
    private static boolean madeMove = false;

    public Detective()
    {
        role = Roles.DETECTIVE;
    }

    @Override
    public IMove makeMove(IPlayer player)
    {
        return () -> player.sendPrivateMessage("ТЫ НА ЗОНУ");
    }

    @Override
    public boolean getMadeMove()
    {
        return madeMove;
    }

    @Override
    public void setMadeMove(boolean madeMove)
    {
        Detective.madeMove = madeMove;
    }
}
