package com.clown.games.mafia.roles;

import net.dv8tion.jda.api.entities.User;

public interface Player
{
    public User getUser();

    public Role getRole();

    public boolean getIsDead();

    public void setIsDead(boolean isDead);

    public boolean getHasVoted();

    public void setHasVoted(boolean hasVoted);

    public String getVotedPlayerName();

    public void setVotedPlayerName(String votedPlayerName);

    public String getPlayerName();

    public void sendPrivateMessage(String text);
}
