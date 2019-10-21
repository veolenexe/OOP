package com.clown.games.mafia.roles;


public abstract class Role implements IRole
{
    private Roles role;

    public Roles getRole()
    {
        return role;
    }

    public void setRole(Roles role)
    {
        this.role = role;
    }

}
