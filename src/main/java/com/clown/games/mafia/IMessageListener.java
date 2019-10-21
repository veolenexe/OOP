package com.clown.games.mafia;

import javax.swing.*;
import java.util.function.Function;

interface IMessageListener
{
    void receiveMessage(String message);
    void setReceivingFunction(Function<String, Boolean> receivingFunction);
}
