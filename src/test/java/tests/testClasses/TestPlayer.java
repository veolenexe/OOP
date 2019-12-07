package tests.testClasses;

import com.clown.games.mafia.player.Player;

public class TestPlayer extends Player {

    private String lastPrivateMessage;
    public TestPlayer(String playerName, String playerID, int playerNumber)
    {
        super(playerName, playerID, playerNumber);
    }

    public String getLastPrivateMessage()
    {
        return lastPrivateMessage;
    }

    @Override
    public void sendPrivateMessage(String message)
    {
        lastPrivateMessage = message;
    }
}
