package com.clown.games.mafia;

import com.clown.games.mafia.roles.*;
import com.clown.games.mafia.roles.Detective;
import com.clown.games.mafia.roles.Doctor;
import com.clown.games.mafia.roles.Mafia;

import java.util.*;


class Game
{
    private List<Player> participants;
    private List<Player> dayDeadPlayers;
    private Map<String, Integer> votes;
    private int alivePlayers;
    private GameState currentState = GameState.OFFLINE;
    private MessageSender messageSender;
    private int dayCount;

    Game()
    {
        participants = new ArrayList<>();
        dayDeadPlayers = new ArrayList<>();
        votes = new HashMap<>();
    }

    void prepareForGame()
    {
        currentState = GameState.PREPARATION;
        sendMessage("Mafia preparation! Write \"!join mafia\" to enter the game.\n" +
                "Write !Mafia start to start the game.");
    }

    void addParticipant(Player participant)
    {
        participants.add(participant);
        sendMessage("There are: " + participants.size() + " participants.");
    }

    boolean isPlayerParticipant(String playerName)
    {
        Optional<Player> player = getPlayerByName(playerName);
        return player.filter(value -> participants.contains(value)).isPresent();
    }

    GameState getCurrentGameState()
    {
        return currentState;
    }

    int getCurrentPlayersCount()
    {
        return participants.size();
    }

    void setMessageSender(MessageSender messageSender)
    {
        this.messageSender = messageSender;
    }


    void startGame()
    {
        sendMessage("игра началась!");
        shuffleRoles();
        beginDayState();
    }

    private void beginDayState()
    {
        currentState = GameState.DAY;
        if (dayCount == 0)
        {
            sendMessage("Welcome - citizens! Greet each other!");
        }
        else
        {
            sendMessage("Morning, citizens! There are some news...");
            sendDayInformation();
            sendMessage("Now it's time to vote! Vote wisely...");
        }

    }

    private void beginNightState()
    {
        currentState = GameState.NIGHT_MAFIA;
        sendMessage("It's night time! Go to bed. NOW!");
    }

    private void sendDayInformation()
    {
        StringBuilder dayInformation = new StringBuilder();
        for (Player player : dayDeadPlayers)
        {
            dayInformation.append("Player ").append(player.getPlayerName()).append(" died.\n");
        }
        dayInformation.append("There are: ").append(alivePlayers).append(" players alive!");
        sendMessage(dayInformation.toString());
    }

    void makeAVote(String playerName, String votingPlayerName)
    {
        Optional<Player> playerOptional = getPlayerByName(playerName);
        Optional<Player> votingPlayerOptional = getPlayerByName(votingPlayerName);

        if (votingPlayerOptional.isEmpty())
        {
            throw new IllegalArgumentException("Wrong player name!");
        }

        Player votingPlayer = votingPlayerOptional.get();

        if (playerName.equals("pass"))
        {
            votingPlayer.setHasVoted(true);
            votingPlayer.setVotedPlayerName("pass");
        }
        else if (playerOptional.isEmpty())
        {
            throw new IllegalArgumentException("Wrong player name!");
        }

        if (votingPlayer.getHasVoted() && votingPlayer.getVotedPlayerName().equals(playerName))
        {
            sendMessage("Dear, " + votingPlayerName + " you cannot vote twice!");
            return;
        }

        if (votes.containsKey(playerName))
        {
            votes.replace(playerName, votes.get(playerName) + 1);
        }
        else
        {
            votes.put(playerName, 1);
        }
        votingPlayer.setHasVoted(true);
        votingPlayer.setVotedPlayerName(playerName);
        if (everyoneHasVoted())
        {
            beginNightState();
        }
    }

    private Optional<Player> getPlayerByName(String playerName)
    {
        return participants.stream()
                .filter(player -> playerName.equals(player.getPlayerName()))
                .findAny();
    }

    private boolean everyoneHasVoted()
    {
        return participants.stream().allMatch(Player::getHasVoted);
    }

    private void shuffleRoles()
    {
        int playerCount = getCurrentPlayersCount();
        int mafiaCount = playerCount / 4;
        Collections.shuffle(participants);
        for (int i = 0; i < mafiaCount; i++)
        {
            participants.set(i, new Mafia(participants.get(i)));
        }
        participants.set(mafiaCount, new Doctor(participants.get(mafiaCount)));
        participants.set(mafiaCount + 1, new Detective(participants.get(mafiaCount + 1)));
        for (Player person : participants)
        {
            messageSender.sendMessageToPlayer("Your role is " + person.getRole(), person);
        }
    }


    private void sendMessage(String message)
    {
        messageSender.sendMessage(message);
    }

}
