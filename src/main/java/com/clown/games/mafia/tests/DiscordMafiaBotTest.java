package com.clown.games.mafia.tests;
import com.clown.games.mafia.Game;
import com.clown.games.mafia.IGame;
import com.clown.games.mafia.discord.DiscordMafiaBot;
import com.clown.games.mafia.discord.DiscordMessageListener;
import com.clown.games.mafia.messaging.IMessageListener;
import com.clown.games.mafia.tests.testClasses.TestMessageListener;
import com.clown.games.mafia.tests.testClasses.TestMessageSender;
import com.clown.games.mafia.tests.testClasses.TestPlayer;
import org.junit.Assert;
import org.mockito.Mock;

public class DiscordMafiaBotTest
{
    private TestMessageListener testMessageListener;
    private IGame game;
    private TestPlayer testPlayer;
    private DiscordMafiaBot discordMafiaBot;

    @org.junit.Before
    public void setUp()
    {
        testMessageListener = new TestMessageListener();
        discordMafiaBot = new DiscordMafiaBot(testMessageListener);
        game = new Game();
        testPlayer = new TestPlayer("Gordon Freeman", "id", 1);
    }

    @org.junit.After
    public void tearDown()
    {
    }

    @org.junit.Test
    public void onMessageReceived_should_recieveMessage()
    {
        testMessageListener.receiveMessage("!Mafia");
        Assert.assertTrue(true);
    }
}
