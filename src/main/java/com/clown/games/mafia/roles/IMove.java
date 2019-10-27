package com.clown.games.mafia.roles;

public interface IMove
{
    int getPriority();
    void setPriority(int priority);
    void act();
}
