package com.clown.games.mafia.roles;

import net.dv8tion.jda.api.entities.User;

public class Mafia extends Citizen
{

    public Mafia(User user)
    {
        super(user);
        this.role = Role.MAFIA;
    }
}
