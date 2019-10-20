package com.clown.games.mafia.roles;

public abstract class BasePlayer implements Player
{
    protected Role role;
    protected String playerName;
    private boolean isDead;
    private boolean hasVoted;
    private String votedPlayerName;

    public BasePlayer(String playerName, Role role)
    {
        this.role = role;
        this.playerName = playerName;
    }

    @Override
    public Role getRole()
    {
        return this.role;
    }

    @Override
    public void setRole(Role role)
    {
        this.role = role;
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

    abstract public void sendPrivateMessage(String text);
}
