package com.clown.games.mafia;

import com.clown.games.mafia.roles.IPlayer;

public interface IMessageSender
{
    void sendMessage(String message);

    void sendMessageToPlayer(String message, IPlayer player);
}
