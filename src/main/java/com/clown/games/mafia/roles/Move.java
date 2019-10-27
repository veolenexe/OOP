package com.clown.games.mafia.roles;

import javax.swing.*;

public class Move implements IMove
{
    private int priority;
    private Action action;

    @Override
    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    @Override
    public int getPriority()
    {
        return priority;
    }

    @Override
    public void act()
    {
    }
}
