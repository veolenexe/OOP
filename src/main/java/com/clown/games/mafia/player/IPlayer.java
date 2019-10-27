package com.clown.games.mafia.player;

import com.clown.games.mafia.roles.IMove;
import com.clown.games.mafia.roles.Roles;

public interface IPlayer
{
    Roles getRole();
    void setRole(Roles role);
    boolean getIsDead();
    void setIsDead(boolean isDead);
    boolean getHasVoted();
    void setHasVoted(boolean hasVoted);
    String getVotedPlayerID();
    void setVotedPlayerID(String votedPlayerID);
    String getPlayerName();
    String getPlayerID();
    int getPlayerNumber();
    IMove makeMove(IPlayer player);
    void sendPrivateMessage(String message);
    int getPlayerMovePriority();
    boolean getMadeMove();
    void setMadeMove(boolean madeMove);
}
