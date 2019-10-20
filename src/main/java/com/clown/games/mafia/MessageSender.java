package com.clown.games.mafia;

import com.clown.games.mafia.roles.Player;

public interface MessageSender
{
    void sendMessage(String message);

    void sendMessageToPlayer(String message, Player player);
}
