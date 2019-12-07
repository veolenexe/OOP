package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

public class Mafia extends Role
{
    private static boolean madeMove = false;

    public Mafia()
    {
        playerMovePriority = 1;
        role = Roles.MAFIA;
    }

    @Override
    public IMove makeMove(IPlayer votingPlayer, IPlayer votedPlayer)
    {
        if(madeMove)
        {
            throw new RuntimeException();
        }
        madeMove = true;
        return () -> votedPlayer.setIsDead(true);
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
