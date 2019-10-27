package com.clown.games.mafia.messaging;

import java.util.function.Function;

public interface IMessageListener
{
    void receiveMessage(String message);
    void setReceivingFunction(Function<String, Boolean> receivingFunction);
}
