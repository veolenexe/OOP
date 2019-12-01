package com.clown.games.mafia.tests;

import com.clown.games.mafia.Game;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.*;
import com.clown.games.mafia.tests.testClasses.TestPlayer;
import org.junit.Assert;

public class RolesTest {
    private IPlayer firstPlayer;
    private IPlayer secondPlayer;
    private Game game;

    @org.junit.Before
    public void setUp()
    {
        game = new Game();
        firstPlayer = new TestPlayer("first", "11", 1);
        secondPlayer = new TestPlayer("second", "22", 2);
        game.addParticipant(firstPlayer);
        game.addParticipant(secondPlayer);
        game.startGame();
    }

    @org.junit.After
    public void tearDown()
    {
        firstPlayer.setMadeMove(false);
        secondPlayer.setMadeMove(false);
    }

    @org.junit.Test
    public void makeMove_should_mafiaKill()
    {
        firstPlayer.setRole(Roles.MAFIA);
        secondPlayer.setRole(Roles.CITIZEN);
        boolean secondPlayerStateBefore = secondPlayer.isAlive();
        game.addMove(firstPlayer.getPlayerMovePriority(), firstPlayer.makeMove(secondPlayer));
        boolean secondPlayerStateAfter = secondPlayer.isDead();
        Assert.assertEquals(secondPlayerStateBefore, secondPlayerStateAfter);
    }

    @org.junit.Test
    public void makeMove_should_correctOrderOne()
    {
        firstPlayer.setRole(Roles.MAFIA);
        secondPlayer.setRole(Roles.DOCTOR);
        boolean firstPlayerStateBefore = firstPlayer.isAlive();
        game.addMove(secondPlayer.getPlayerMovePriority(), secondPlayer.makeMove(firstPlayer));
        game.addMove(firstPlayer.getPlayerMovePriority(), firstPlayer.makeMove(secondPlayer));
        boolean firstPlayerStateAfter = firstPlayer.isAlive();
        Assert.assertEquals(firstPlayerStateBefore, firstPlayerStateAfter);
    }

    @org.junit.Test
    public void makeMove_should_correctOrderTwo()
    {
        firstPlayer.setRole(Roles.MAFIA);
        secondPlayer.setRole(Roles.DOCTOR);
        boolean firstPlayerStateBefore = firstPlayer.isAlive();
        boolean secondPlayerStateBefore = secondPlayer.isAlive();
        game.addMove(secondPlayer.getPlayerMovePriority(), secondPlayer.makeMove(firstPlayer));
        game.addMove(firstPlayer.getPlayerMovePriority(), firstPlayer.makeMove(secondPlayer));
        boolean firstPlayerStateAfter = firstPlayer.isAlive();
        boolean secondPlayerStateAfter = secondPlayer.isDead();
        boolean firstPlayerCorrectState = firstPlayerStateBefore == firstPlayerStateAfter;
        boolean secondPlayerCorrectState = secondPlayerStateBefore == secondPlayerStateAfter;
        Assert.assertEquals(firstPlayerCorrectState, secondPlayerCorrectState);
    }

    @org.junit.Test
    public void makeMove_should_killOnePersonPerDay()
    {
        firstPlayer.setRole(Roles.MAFIA);
        secondPlayer.setRole(Roles.MAFIA);
        game.addMove(firstPlayer.getPlayerMovePriority(), firstPlayer.makeMove(secondPlayer));
        game.addMove(secondPlayer.getPlayerMovePriority(), secondPlayer.makeMove(firstPlayer));
        Assert.assertEquals(3, game.getDayCount());
    }
}
