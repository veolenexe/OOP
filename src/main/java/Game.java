import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.*;


public class Game
{
    private ArrayList<Player> participants;
    private ArrayList<Player> dayDeadPlayers;
    private Map<String, Integer> votes;
    private int alivePlayers;
    private GameState currentState = GameState.OFFLINE;
    private TextChannel channel;
    private int dayCount;

    public Game()
    {
        participants = new ArrayList<Player>();
        dayDeadPlayers = new ArrayList<Player>();
        votes = new HashMap<String, Integer>();
    }

    public void prepareForGame()
    {
        currentState = GameState.PREPARATION;
        sendMessageToChannel("Mafia preparation! Write \"!join mafia\" to enter the game.\n" +
                                      "Write !Mafia start to start the game.");
    }

    public void addParticipant(User participant)
    {
        Player newParticipant = new Citizen(participant);
        participants.add(newParticipant);
        sendMessageToChannel("There are: " + participants.size() + " participants.");
    }

    public boolean isPlayerParticipant(User player)
    {
        return participants.contains(player);
    }

    public GameState getCurrentGameState()
    {
        return currentState;
    }

    public int getCurrentPlayersCount()
    {
        return participants.size();
    }

    public void setChannel(TextChannel channel)
    {
        this.channel = channel;
    }

    public TextChannel getChannel()
    {
        return channel;
    }

    public void mafiaStart()
    {
        sendMessageToChannel("игра началась!");
        shuffleRoles();
        beginDayState();
    }

    private void beginDayState()
    {
        currentState = GameState.DAY;
        if(dayCount == 0)
            sendMessageToChannel("Welcome - citizens! Greet each other!");
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
            dayInformation.append("Player ").append(player.getPlayerName()).append(" died.\n");
        dayInformation.append("There are: ").append(alivePlayers).append(" players alive!");
        sendMessageToChannel(dayInformation.toString());
    }

    public void makeAVote(String playerName, String votingPlayerName)
    {
        Player player = getPlayerByName(playerName);
        Player votingPlayer = getPlayerByName(votingPlayerName);

        if(votingPlayer == null)
            throw new IllegalArgumentException("Wrong player name!");
        if(playerName.equals("pass"))
        {
            votingPlayer.setHasVoted(true);
            votingPlayer.setVotedPlayerName("pass");
        }
        else if(player == null)
            throw new IllegalArgumentException("Wrong player name!");

        if(votingPlayer.getHasVoted() && votingPlayer.getVotedPlayerName().equals(playerName))
        {
            sendMessageToChannel("Dear, " + votingPlayerName + " you cannot vote twice!");
            return;
        }

        if(votes.containsKey(playerName))
            votes.replace(playerName, votes.get(playerName) + 1);
        else
            votes.put(playerName, 1);
        votingPlayer.setHasVoted(true);
        votingPlayer.setVotedPlayerName(playerName);
        if(everyoneHasVoted())
            beginNightState();
    }

    private Player getPlayerByName(String playerName)
    {
        for (Player player:participants)
            if (player.getPlayerName().equals(playerName))
                return player;
        return null;
    }

    private boolean everyoneHasVoted()
    {
        for (Player player:participants)
        {
            if(!player.getHasVoted())
                return false;
        }
        return true;
    }

    private void shuffleRoles()
    {
        int playerCount = getCurrentPlayersCount();
        int mafiaCount = (int)(playerCount/4);
        Collections.shuffle(participants);
        for (int i = 0 ; i < mafiaCount; i++)
            participants.set(i,new Mafia(participants.get(i).getUser()));
        participants.set(mafiaCount,new Doctor(participants.get(mafiaCount).getUser()));
        participants.set(mafiaCount+1,new Detective(participants.get(mafiaCount+1).getUser()));
        for (Player person:participants)
        {
            person.sendPrivateMessage("Your role is " + person.getRole());
        }
    }


    private void sendMessageToChannel(String message)
    {
        channel.sendMessage(message).queue();
    }

}
