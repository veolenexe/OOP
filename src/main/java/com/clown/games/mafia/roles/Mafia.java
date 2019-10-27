package com.clown.games.mafia.roles;

import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.player.Player;

import javax.swing.*;
import java.util.List;
import java.util.function.Function;

public class Mafia extends Role
{
    public Mafia()
    {
        role = Roles.MAFIA;
    }

    @Override
    public Move makeMove(List<IPlayer> players)
    {
        players.get(0).sendPrivateMessage("aaa");
        Move move = new Move();
        return move;
    }
}
