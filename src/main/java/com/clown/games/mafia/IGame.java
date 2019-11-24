package com.clown.games.mafia;

import com.clown.games.mafia.messaging.IMessageSender;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.IMove;

import java.util.Optional;

public interface IGame
{
    void prepareForGame();
    void startGame();
    Optional<IPlayer> getPlayerByID(String playerID);
    Optional<IPlayer> getPlayerByNumber(int playerNumber);
    void addParticipant(IPlayer participant);

    void setMessageSender(IMessageSender gameMessageSender);

    int getCurrentPlayersCount();

    boolean isPlayerParticipant(String id);

    GameState getCurrentGameState();

    void sendMessage(String message);

    void addMove(int movePriority, IMove move);

    void makeAVote(String playersVote, String votingPlayerID);
}
