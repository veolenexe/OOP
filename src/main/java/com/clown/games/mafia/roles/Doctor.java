package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

public class Doctor extends Role
{
    private static boolean madeMove = false;

    public Doctor()
    {
        playerMovePriority = 2;
        role = Roles.DOCTOR;
    }

    @Override
    public IMove makeMove(IPlayer votingPlayer, IPlayer votedPlayer)
    {
        if(madeMove)
        {
            throw new RuntimeException();
        }
        madeMove = true;
        return () -> votedPlayer.setIsDead(false);
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
