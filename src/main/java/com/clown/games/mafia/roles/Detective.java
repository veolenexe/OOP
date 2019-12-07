package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

public class Detective extends Role
{
    private static boolean madeMove = false;

    public Detective()
    {
        playerMovePriority = 3;
        role = Roles.DETECTIVE;
    }

    @Override
    public IMove makeMove(IPlayer votingPlayer, IPlayer votedPlayer)
    {
        if(madeMove)
        {
            throw new RuntimeException();
        }
        madeMove = true;
        return () -> votingPlayer.sendPrivateMessage(votedPlayer.getRole().toString());
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
