package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.player.Player;

import javax.swing.*;
import java.util.List;
import java.util.function.Function;

public class Mafia extends Role
{
    private static boolean madeMove = false;

    public Mafia()
    {
        role = Roles.MAFIA;
    }

    @Override
    public IMove makeMove(IPlayer player)
    {
        return () -> player.sendPrivateMessage("ТЫ СДОХ");
    }

    @Override
    public boolean getMadeMove()
    {
        return madeMove;
    }

    @Override
    public void setMadeMove(boolean madeMove)
    {
        Mafia.madeMove = madeMove;
    }
}
