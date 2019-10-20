package com.clown.games.mafia.roles;

import java.util.List;

public interface Player
{
    Role getRole();

    void setRole(Role role);

    boolean getIsDead();

    void setIsDead(boolean isDead);

    boolean getHasVoted();

    void setHasVoted(boolean hasVoted);

    String getVotedPlayerName();

    void setVotedPlayerName(String votedPlayerName);

    String getPlayerName();

    void makeMove(List<Player> players);
}
