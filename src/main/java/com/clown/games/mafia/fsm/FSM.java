package com.clown.games.mafia.fsm;

public class FSM implements IFSM
{
    private IState activeState;

    public void setState(IState state)
    {
        activeState = state;
    }

    public void update()
    {
        if (activeState != null)
        {
            activeState.actState();
        }
    }
}
