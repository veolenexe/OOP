package com.clown.games.mafia.roles;

import java.util.List;

public interface IRole
{
    void makeMove(List<IPlayer> players);
    Roles getRole();
    void setRole(Roles role);
}
