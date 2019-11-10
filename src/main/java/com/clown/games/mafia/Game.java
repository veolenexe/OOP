package com.clown.games.mafia;

import com.clown.games.mafia.fsm.FSM;
import com.clown.games.mafia.fsm.IFSM;
import com.clown.games.mafia.messaging.IMessageSender;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.*;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.util.*;


public class Game
{
    private List<IPlayer> participants;
    private List<Pair<Integer, IMove>> moves;
    private Map<String, Integer> votes;
    private int mafiaCount;
    private int alivePlayers;
    private GameState currentState = GameState.OFFLINE;
    private IMessageSender messageSender;
    private int dayCount;
    private IFSM fsm;

    public Game()
    {
        participants = new ArrayList<>();
        moves = new ArrayList<>();
        votes = new HashMap<>();
        fsm = new FSM();
    }

    public void prepareForGame()
    {
        currentState = GameState.PREPARATION;
        sendMessage("Mafia preparation! Write \"!join mafia\" to enter the game.\n" +
                "Write !Mafia start to start the game.");
    }

    public void startGame()
    {
        sendMessage("игра началась!");
        shuffleRoles();
        while (isGameActive())
        {
            fsm.update();
        }
    }

    private void beginDayState()
    {
        currentState = GameState.DAY;
        if (dayCount == 0)
        {
            sendMessage("Welcome - citizens! Greet each other!");
            alivePlayers = getCurrentPlayersCount();
            sendMessage("Write !Mafia night to begin the game!");
        }
        else
        {
            sendMessage("Morning, citizens! There are some news...");
            sendDayInformation();
            sendMessage("Now it's time to vote! Vote wisely...\n");
            sendMessage("Write '!Vote 1', to vote for first player, and so on...\n");
        }
        dayCount++;
    }

    public void addMove(int movePriority, IMove move)
    {
        moves.add(Pair.of(movePriority, move));
        moves.sort(Comparator.comparing(Pair::getLeft));
        if (everyoneMadeMove())
        {
            actMoves();
            fsm.setState(this::beginDayState);
        }
    }

    private boolean everyoneMadeMove()
    {
        return participants.stream().allMatch(IPlayer::getMadeMove);
    }

    private void actMoves()
    {
        for (Pair<Integer, IMove> move : moves)
        {
            move.getRight().act();
        }
    }

    private void sendDayInformation()
    {
        StringBuilder dayInformation = new StringBuilder();
        dayInformation.append("There are: ").append(alivePlayers).append(" players alive!\n");

        List<IPlayer> newParticipants = new ArrayList<>();

        for (IPlayer player : participants)
        {
            if (player.getIsDead())
            {
                alivePlayers--;
                if (player.getRole()==Roles.MAFIA)
                {
                    mafiaCount--;
                }
                String playerName = player.getPlayerName();
                String playerNumber = Integer.toString(player.getPlayerNumber());
                String playerInfo = playerNumber + ". " + playerName;
                dayInformation.append("Player ").append(playerInfo).append(" died.\n");
            }
        }

        for (IPlayer player : participants)
        {
            if (player.getIsDead())
            {
                continue;
            }
            newParticipants.add(player);
            String playerName = player.getPlayerName();
            String playerNumber = Integer.toString(player.getPlayerNumber());
            String playerInfo = playerNumber + ". " + playerName;
            dayInformation.append(playerInfo);
        }
        participants = newParticipants;
        sendMessage(dayInformation.toString());
    }

    private void beginNightState()
    {
        currentState = GameState.NIGHT;
        sendMessage("It's night time! Go to bed. NOW!");
        for (IPlayer player : participants)
        {
            if (player.getRole() != Roles.CITIZEN)
            {
                player.sendPrivateMessage("choose player to make move");
                player.sendPrivateMessage(formatToStringPlayerList());
            }
        }

    }

    public void makeAVote(String playersVote, String votingPlayerID)
    {
        Optional<IPlayer> votingPlayerOptional = getPlayerByID(votingPlayerID);
        if (votingPlayerOptional.isEmpty())
        {
            throw new IllegalArgumentException("Wrong player name!");
        }
        IPlayer votingPlayer = votingPlayerOptional.get();

        if (IsWrongVote(playersVote))
        {
            sendMessage("Dear, " + votingPlayerID + " you wrote wrong vote");
            return;
        }

        if (playersVote.equals("pass"))
        {
            votingPlayer.setHasVoted(true);
            votingPlayer.setVotedPlayerID("pass");
            return;
        }

        int playerNumberToVote = Integer.parseInt(playersVote);

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
            fsm.setState(this::beginNightState);
        }
    }

    private Boolean IsWrongVote(String playersVote)
    {
        int playerNumberToVote;
        try
        {
            playerNumberToVote = Integer.parseInt(playersVote);
            return getPlayerByNumber(playerNumberToVote).isEmpty();
        }
        catch (NumberFormatException e)
        {
            return !playersVote.equals("pass");
        }
    }

    private String formatToStringPlayerList()
    {
        StringBuilder result = new StringBuilder();
        for (IPlayer player : participants)
        {
            result.append(player.getPlayerNumber()).append(". ").append(player.getPlayerName()).append("\n");
        }
        return result.toString();
    }

    public Optional<IPlayer> getPlayerByID(String playerID)
    {
        return participants.stream()
                .filter(player -> playerID.equals(player.getPlayerID()))
                .findAny();
    }

    public Optional<IPlayer> getPlayerByNumber(int playerNumber)
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
        mafiaCount = playerCount / 4;
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
        for (int i = citizenIndex; i < playerCount; i++)
        {
            participants.get(i).setRole(Roles.CITIZEN);
        }
        for (IPlayer person : participants)
        {
            person.sendPrivateMessage("Your role is " + person.getRole());
        }
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

    private boolean isGameActive()
    {
        return alivePlayers/2 > mafiaCount;
    }
    private void sendMessage(String message)
    {
        messageSender.sendMessage(message);
    }

}
