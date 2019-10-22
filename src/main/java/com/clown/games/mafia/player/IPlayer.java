package com.clown.games.mafia.player;

import com.clown.games.mafia.roles.Roles;

import java.util.List;

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

    void makeMove(List<IPlayer> players);

    void sendPrivateMessage(String message);
}
