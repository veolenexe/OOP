package com.clown.games.mafia.roles;

import net.dv8tion.jda.api.entities.User;

public class Doctor extends Citizen
{
    public Doctor(User user)
    {
        super(user);
        this.role = Role.DOCTOR;
    }
}
