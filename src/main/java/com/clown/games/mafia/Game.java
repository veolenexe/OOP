package com.clown.games.mafia;

import com.clown.games.mafia.roles.*;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.*;


class Game
{
    private List<Player> participants;
    private List<Player> dayDeadPlayers;
    private Map<String, Integer> votes;
    private int alivePlayers;
    private GameState currentState = GameState.OFFLINE;
    private TextChannel channel;
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
        sendMessageToChannel("com.clown.games.mafia.roles.Mafia preparation! Write \"!join mafia\" to enter the game.\n" +
                "Write !com.clown.games.mafia.roles.Mafia start to start the game.");
    }

    void addParticipant(User participant)
    {
        Player newParticipant = new Citizen(participant);
        participants.add(newParticipant);
        sendMessageToChannel("There are: " + participants.size() + " participants.");
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

    void setChannel(TextChannel channel)
    {
        this.channel = channel;
    }

    TextChannel getChannel()
    {
        return channel;
    }

    void mafiaStart()
    {
        sendMessageToChannel("игра началась!");
        shuffleRoles();
        beginDayState();
    }

    private void beginDayState()
    {
        currentState = GameState.DAY;
        if (dayCount == 0)
        {
            sendMessageToChannel("Welcome - citizens! Greet each other!");
        }
        else
        {
            sendMessageToChannel("Morning, citizens! There are some news...");
            sendDayInformation();
            sendMessageToChannel("Now it's time to vote! Vote wisely...");
        }

    }

    private void beginNightState()
    {
        currentState = GameState.NIGHT_MAFIA;
        sendMessageToChannel("It's night time! Go to bed. NOW!");
    }

    private void sendDayInformation()
    {
        StringBuilder dayInformation = new StringBuilder();
        for (Player player : dayDeadPlayers)
        {
            dayInformation.append("com.clown.games.mafia.roles.Player ").append(player.getPlayerName()).append(" died.\n");
        }
        dayInformation.append("There are: ").append(alivePlayers).append(" players alive!");
        sendMessageToChannel(dayInformation.toString());
    }

    void makeAVote(String playerName, String votingPlayerName)
    {
        Optional<Player> playerOptional = getPlayerByName(playerName);
        Optional<Player> votingPlayerOptional = getPlayerByName(votingPlayerName);

        if (!votingPlayerOptional.isPresent())
        {
            throw new IllegalArgumentException("Wrong player name!");
        }

        Player votingPlayer = votingPlayerOptional.get();

        if (playerName.equals("pass"))
        {
            votingPlayer.setHasVoted(true);
            votingPlayer.setVotedPlayerName("pass");
        }
        else if (!playerOptional.isPresent())
        {
            throw new IllegalArgumentException("Wrong player name!");
        }

        if (votingPlayer.getHasVoted() && votingPlayer.getVotedPlayerName().equals(playerName))
        {
            sendMessageToChannel("Dear, " + votingPlayerName + " you cannot vote twice!");
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
        for (Player player : participants)
        {
            if (!player.getHasVoted())
            {
                return false;
            }
        }
        return true;
    }

    private void shuffleRoles()
    {
        int playerCount = getCurrentPlayersCount();
        int mafiaCount = playerCount / 4;
        Collections.shuffle(participants);
        for (int i = 0; i < mafiaCount; i++)
        {
            participants.set(i, new Mafia(participants.get(i).getUser()));
        }
        participants.set(mafiaCount, new Doctor(participants.get(mafiaCount).getUser()));
        participants.set(mafiaCount + 1, new Detective(participants.get(mafiaCount + 1).getUser()));
        for (Player person : participants)
        {
            person.sendPrivateMessage("Your role is " + person.getRole());
        }
    }


    private void sendMessageToChannel(String message)
    {
        channel.sendMessage(message).queue();
    }

}
