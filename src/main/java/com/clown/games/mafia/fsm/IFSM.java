package com.clown.games.mafia.fsm;

public interface IFSM
{
    void setState(IState state);

    void update();

}
