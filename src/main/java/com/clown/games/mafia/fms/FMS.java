package com.clown.games.mafia.fms;

public abstract class FMS implements IFMS
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
