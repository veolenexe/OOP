import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import java.util.Collections;
import java.util.List;


public class Game
{
    private List<Player> participants;
    private GameState currentState = GameState.OFFLINE;
    private TextChannel channel;
    private int DayCount;

    public void prepareForGame(TextChannel mafiaChannel)
    {
        channel = mafiaChannel;
        currentState = GameState.PREPARATION;
        channel.sendMessage("Mafia preparation! Write \"!join mafia\" to enter the game.\n" +
                                      "Write !Mafia start to start the game.").queue();
    }

    public void addParticipant(User participant)
    {
        Player newParticipant = new Player(participant, Role.CITIZEN);
        participants.add(newParticipant);
        channel.sendMessage("There are: " + participants.size() + " participants.").queue();
    }

    public boolean isPlayerParticipant(User player)
    {
        return participants.contains(player);
    }

    public GameState checkCurrentGameState()
    {
        return currentState;
    }

    public int checkCurrentPlayersCount()
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
        channel.sendMessage("игра началась!").queue();
        shuffleRoles();
        //startGame(); начало игры
    }

    private void shuffleRoles()
    {
        int playerCount = checkCurrentPlayersCount();
        int mafiaCount = (int)(playerCount/4);
        Collections.shuffle(participants);
        for (int i = 0 ; i < mafiaCount; i++)
            participants.get(i).setRole(Role.MAFIA);
        participants.get(mafiaCount).setRole(Role.DOCTOR);
        participants.get(mafiaCount+1).setRole(Role.DETECTIVE);
        for (Player person:participants)
        {
            person.sendPrivateMessage("Your role is " + person.getRole());
        }
    }


}
