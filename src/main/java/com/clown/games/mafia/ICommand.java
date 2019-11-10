package com.clown.games.mafia;

import net.dv8tion.jda.api.entities.User;

@FunctionalInterface
public interface ICommand
{
    void handleCommand(String command, Game game, User user);
}
