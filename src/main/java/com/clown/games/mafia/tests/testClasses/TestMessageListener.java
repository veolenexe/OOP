package com.clown.games.mafia.tests.testClasses;
import com.clown.games.mafia.discord.DiscordMessageListener;
import java.util.function.Function;

public class TestMessageListener extends DiscordMessageListener
{
    private Function<String, Boolean> receivingFunction;

    @Override
    public void receiveMessage(String message)
    {
        receivingFunction.apply(message);
    }

    @Override
    public void setReceivingFunction(Function<String, Boolean> receivingFunction)
    {
        this.receivingFunction = receivingFunction;
    }
}
