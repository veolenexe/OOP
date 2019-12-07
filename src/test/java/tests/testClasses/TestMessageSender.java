package tests.testClasses;

import com.clown.games.mafia.messaging.IMessageSender;

public class TestMessageSender implements IMessageSender
{
    private String lastMessage;
    @Override
    public void sendMessage(String message)
    {
        lastMessage = message;
    }

    public String getLastMessage()
    {
        return lastMessage;
    }
}
