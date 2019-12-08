package com.clown.games.mafia;

import com.clown.games.mafia.messaging.IMessageSender;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.IMove;
import com.clown.games.mafia.roles.Roles;

import java.util.List;
import java.util.Optional;

public interface IGame
{
    void prepareForGame();
    void startGame();
    Optional<IPlayer> getPlayerByID(String playerID);
    Optional<IPlayer> getPlayerByNumber(int playerNumber);
    List<IPlayer> getParticipantsByRole(Roles role);
    List<IPlayer> getParticipantsExcludingRole(Roles role);
    List<IPlayer> getParticipants();
    boolean getMafiaWon();
    void addParticipant(IPlayer participant);

    void setMessageSender(IMessageSender gameMessageSender);

    int getCurrentPlayersCount();

    boolean isPlayerParticipant(String id);

    GameState getCurrentGameState();

    void sendMessage(String message);

    void addMove(int movePriority, IMove move);

    boolean getHasEnded();

    void makeAVote(String playersVote, String votingPlayerID);
}
