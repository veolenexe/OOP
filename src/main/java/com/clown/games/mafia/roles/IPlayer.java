package com.clown.games.mafia.roles;

import java.util.List;

public interface IPlayer
{
    Roles getRole();

    void setRole(Roles role);

    boolean getIsDead();

    void setIsDead(boolean isDead);

    boolean getHasVoted();

    void setHasVoted(boolean hasVoted);

    String getVotedPlayerName();

    void setVotedPlayerName(String votedPlayerName);

    String getPlayerName();

    void makeMove(List<IPlayer> players);

    void sendPrivateMessage(String message);
}
