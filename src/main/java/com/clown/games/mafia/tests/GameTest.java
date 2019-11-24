package com.clown.games.mafia.tests;

import com.clown.games.mafia.Game;
import com.clown.games.mafia.GameState;
import com.clown.games.mafia.IGame;
import  com.clown.games.mafia.tests.testClasses.TestPlayer;
import  com.clown.games.mafia.tests.testClasses.TestMessageSender;
import org.junit.Assert;

public class GameTest {

    private IGame game;
    private TestPlayer testPlayer;
    private TestMessageSender sender;

    @org.junit.Before
    public void setUp()
    {
        game = new Game();
        sender = new TestMessageSender();
        game.setMessageSender(sender);
        testPlayer = new TestPlayer("Gordon Freeman", "lolid", 1);
    }

    @org.junit.Test
    public void prepareForGame_should_changeGameState()
    {
        game.prepareForGame();
        Assert.assertEquals(GameState.PREPARATION, game.getCurrentGameState());
    }

    @org.junit.Test
    public void startGame_should_changeGameStateToNight()
    {
        game.startGame();
        Assert.assertEquals(GameState.NIGHT, game.getCurrentGameState());
    }

    @org.junit.Test
    public void makeAVote_should_sendMessageOnWrongVote()
    {
        game.addParticipant(testPlayer);
        game.makeAVote("9",testPlayer.getPlayerID());
    }

    @org.junit.Test
    public void getPlayerByID_should_returnPlayerByID()
    {
        game.addParticipant(testPlayer);
        var optional = game.getPlayerByID(testPlayer.getPlayerID());
        Assert.assertTrue(optional.isPresent());
    }

    @org.junit.Test
    public void getPlayerByNumber_should_returnPlayerByNumber()
    {
        game.addParticipant(testPlayer);
        var optional = game.getPlayerByNumber(testPlayer.getPlayerNumber());
        Assert.assertTrue(optional.isPresent());
    }

    @org.junit.Test
    public void addParticipant_should_addPlayerToTheGame()
    {
        game.addParticipant(testPlayer);
        Assert.assertEquals(1, game.getCurrentPlayersCount());
    }

    @org.junit.Test
    public void isPlayerParticipant_should_returnTrue_when_playerIsParticipant()
    {
        game.addParticipant(testPlayer);
        Assert.assertTrue(game.isPlayerParticipant(testPlayer.getPlayerID()));
    }

    @org.junit.Test
    public void getCurrentGameState_should_returnOfflineState_when_gameWasNotStarted()
    {
        Assert.assertEquals(GameState.OFFLINE, game.getCurrentGameState());
    }

    @org.junit.Test
    public void getCurrentPlayersCount_should_return0_when_noPlayers()
    {
        Assert.assertEquals(0, game.getCurrentPlayersCount() );
    }

    @org.junit.Test
    public void sendMessage()
    {

    }
}