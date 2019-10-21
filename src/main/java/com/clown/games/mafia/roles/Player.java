package com.clown.games.mafia.roles;

public abstract class Player implements IPlayer
{
    protected IRole role;

    protected String playerName;
    private boolean isDead;
    private boolean hasVoted;
    private String votedPlayerName;

    public Player(String playerName, Roles role)
    {
        this.role.setRole(role);
        this.playerName = playerName;
    }

    @Override
    public Roles getRole()
    {
        return role.getRole();
    }

    @Override
    public void setRole(Roles role)
    {
        this.role.setRole(role);
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
    public String getVotedPlayerName()
    {
        return votedPlayerName;
    }

    @Override
    public void setVotedPlayerName(String votedPlayerName)
    {
        this.votedPlayerName = votedPlayerName;
    }

    @Override
    public String getPlayerName()
    {
        return playerName;
    }
}
