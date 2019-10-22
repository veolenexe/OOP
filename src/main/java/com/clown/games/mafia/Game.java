package com.clown.games.mafia;

import com.clown.games.mafia.messaging.IMessageSender;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.*;

import java.util.*;


public class Game
{
    private List<IPlayer> participants;
    private List<IPlayer> dayDeadPlayers;
    private Map<String, Integer> votes;
    private int alivePlayers;
    private GameState currentState = GameState.OFFLINE;
    private IMessageSender messageSender;
    private int dayCount;

    public Game()
    {
        participants = new ArrayList<>();
        dayDeadPlayers = new ArrayList<>();
        votes = new HashMap<>();
    }

    public void prepareForGame()
    {
        currentState = GameState.PREPARATION;
        sendMessage("Mafia preparation! Write \"!join mafia\" to enter the game.\n" +
                "Write !Mafia start to start the game.");
    }

    public void addParticipant(IPlayer participant)
    {
        participants.add(participant);
        sendMessage("There are: " + participants.size() + " participants.");
    }

    public boolean isPlayerParticipant(String playerID)
    {
        Optional<IPlayer> player = getPlayerByID(playerID);
        return player.filter(value -> participants.contains(value)).isPresent();
    }

    public GameState getCurrentGameState()
    {
        return currentState;
    }

    public int getCurrentPlayersCount()
    {
        return participants.size();
    }

    public void setMessageSender(IMessageSender messageSender)
    {
        this.messageSender = messageSender;
    }


    public void startGame()
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
            alivePlayers = getCurrentPlayersCount();
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
        for (IPlayer player : dayDeadPlayers)
        {
            dayInformation.append("Player ").append(player.getPlayerName()).append(" died.\n");
        }
        dayInformation.append("There are: ").append(alivePlayers).append(" players alive!");
        sendMessage(dayInformation.toString());
    }

    public void makeAVote(String playerNumber, String votingPlayerID)
    {
        Optional<IPlayer> votingPlayerOptional = getPlayerByID(votingPlayerID);
        if (votingPlayerOptional.isEmpty())
        {
            throw new IllegalArgumentException("Wrong player name!");
        }
        IPlayer votingPlayer = votingPlayerOptional.get();

        int playerNumberToVote;

        try
        {
            playerNumberToVote = Integer.parseInt(playerNumber);
        }
        catch (NumberFormatException e)
        {
            if (playerNumber.equals("pass"))
            {
                votingPlayer.setHasVoted(true);
                votingPlayer.setVotedPlayerID("pass");
                return;
            }
            else
            {
                throw new IllegalArgumentException("Wrong player number!");
            }
        }

        Optional<IPlayer> playerOptional = getPlayerByNumber(playerNumberToVote);

        if (playerOptional.isEmpty())
        {
            throw new IllegalArgumentException("Wrong player number!");
        }

        IPlayer votedPlayer = playerOptional.get();
        String votedPlayerID = votedPlayer.getVotedPlayerID();

        if (votingPlayer.getHasVoted() && votingPlayer.getVotedPlayerID().equals(votedPlayerID))
        {
            sendMessage("Dear, " + votingPlayerID + " you cannot vote twice!");
            return;
        }

        if (votes.containsKey(votedPlayerID))
        {
            votes.replace(votedPlayerID, votes.get(votedPlayerID) + 1);
        }
        else
        {
            votes.put(votedPlayerID, 1);
        }
        votingPlayer.setHasVoted(true);
        votingPlayer.setVotedPlayerID(votedPlayerID);
        if (everyoneHasVoted())
        {
            beginNightState();
        }
    }

    private Optional<IPlayer> getPlayerByID(String playerID)
    {
        return participants.stream()
                .filter(player -> playerID.equals(player.getPlayerID()))
                .findAny();
    }

    private Optional<IPlayer> getPlayerByNumber(int playerNumber)
    {
        return participants.stream()
                .filter(player -> playerNumber == player.getPlayerNumber())
                .findAny();
    }

    private boolean everyoneHasVoted()
    {
        return participants.stream().allMatch(IPlayer::getHasVoted);
    }

    private void shuffleRoles()
    {
        int playerCount = getCurrentPlayersCount();
        int mafiaCount = playerCount / 4;
        int doctorIndex = mafiaCount;
        int detectiveIndex = mafiaCount + 1;
        int citizenIndex = mafiaCount + 2;
        Collections.shuffle(participants);
        for (int i = 0; i < mafiaCount; i++)
        {
            participants.get(i).setRole(Roles.MAFIA);
        }
        participants.get(doctorIndex).setRole(Roles.DOCTOR);
        participants.get(detectiveIndex).setRole(Roles.DETECTIVE);
        for (int i = citizenIndex; i< playerCount; i++)
        {
            participants.get(i).setRole(Roles.CITIZEN);
        }
        for (IPlayer person : participants)
        {
            person.sendPrivateMessage("Your role is " + person.getRole());
        }
    }


    private void sendMessage(String message)
    {
        messageSender.sendMessage(message);
    }

}
