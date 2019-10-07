package com.clown.games.mafia.roles;

import net.dv8tion.jda.api.entities.User;

public class Detective extends Citizen {
    public Detective(User user) {
        super(user);
        this.role = Role.DETECTIVE;
    }
}
