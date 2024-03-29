package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

public interface IRole
{
    IMove makeMove(IPlayer votingPlayer, IPlayer votedPlayer);
    Roles getRole();
    int getPlayerMovePriority();
    boolean getMadeMove();
    void setMadeMove(boolean madeMove);
}
