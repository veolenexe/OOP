package com.clown.games.mafia;

import javax.swing.*;
import java.util.function.Function;

interface MessageListener
{
    void receiveMessage(String message);
    void setReceivingFunction(Function<String, Boolean> receivingFunction);
}
