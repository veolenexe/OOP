package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;

import java.util.List;

public interface IRole
{
    void makeMove(List<IPlayer> players);
    Roles getRole();
}
