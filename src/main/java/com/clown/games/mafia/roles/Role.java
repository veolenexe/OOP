package com.clown.games.mafia.roles;

public abstract class Role implements IRole
{
    Roles role;
    private int playerMovePriority;

    public Roles getRole()
    {
        return role;
    }

    public static IRole makeRole(Roles role)
    {
        switch (role)
        {
            case MAFIA:
            {
                return new Mafia();
            }
            case DOCTOR:
            {
                return new Doctor();
            }
            case DETECTIVE:
            {
                return new Detective();
            }
            default:
            {
                return new Citizen();
            }
        }
    }

    public int getPlayerMovePriority()
    {
        return playerMovePriority;
    }
}
