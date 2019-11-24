package com.clown.games.mafia.tests.testClasses;

import com.clown.games.mafia.player.Player;

public class TestPlayer extends Player {

    public TestPlayer(String playerName, String playerID, int playerNumber)
    {
        super(playerName, playerID, playerNumber);
    }

    @Override
    public void sendPrivateMessage(String message)
    {
        return;
    }
}
