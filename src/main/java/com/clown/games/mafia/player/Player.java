package com.clown.games.mafia.player;

import com.clown.games.mafia.roles.IRole;
import com.clown.games.mafia.roles.Role;
import com.clown.games.mafia.roles.Roles;

import java.util.List;

public abstract class Player implements IPlayer
{
    protected IRole role;
    private String playerID;
    private int playerNumber;
    protected String playerName;
    private boolean isDead;
    private boolean hasVoted;
    private String votedPlayerID;

    public Player(String playerName, String playerID, int playerNumber)
    {
        this.playerName = playerName;
        this.playerID = playerID;
        this.playerNumber = playerNumber;
    }

    @Override
    public Roles getRole()
    {
        return role.getRole();
    }

    @Override
    public void setRole(Roles role)
    {
        this.role = Role.makeRole(role);
    }

    @Override
    public boolean getIsDead()
    {
        return isDead;
    }

    @Override
    public void setIsDead(boolean isDead)
    {
        this.isDead = isDead;
    }

    @Override
    public boolean getHasVoted()
    {
        return hasVoted;
    }

    @Override
    public void setHasVoted(boolean hasVoted)
    {
        this.hasVoted = hasVoted;
    }

    @Override
    public String getVotedPlayerID()
    {
        return getVotedPlayerID();
    }

    @Override
    public void makeMove(List<IPlayer> players)
    {

        role.makeMove(players);
    }

    @Override
    public void setVotedPlayerID(String votedPlayerID)
    {
        this.votedPlayerID = votedPlayerID;
    }

    @Override
    public String getPlayerName()
    {
        return playerName;
    }

    @Override
    public String getPlayerID()
    {
        return playerID;
    }

    @Override
    public int getPlayerNumber()
    {
        return playerNumber;
    }
}
