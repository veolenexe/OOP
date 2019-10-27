package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public interface IRole
{
    IMove makeMove(IPlayer player);
    Roles getRole();
    int getPlayerMovePriority();
    boolean getMadeMove();
    void setMadeMove(boolean madeMove);
}
