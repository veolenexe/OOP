import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Game
{
    private List<Player> participants;
    private GameState currentState;
    private  TextChannel channel;

    public void prepareForGame(TextChannel mafiaChannel)
    {
        channel = mafiaChannel;
        currentState = GameState.PREPARATION;
        channel.sendMessage("Mafia preparation! Write !participate to enter the game.\n" +
                                      "Write !Mafia_start to start the game.").queue();
    }

    public void addParticipant(User participant)
    {
        Player newParticipant = new Player(participant, Role.CITIZEN);
        participants.add(newParticipant);
        channel.sendMessage("There are: " + participants.size() + " participants.").queue();
    }
}
