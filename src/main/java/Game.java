import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Game
{
    public List<Player> participants;
    public GameState currentState;

    public void prepareForGame(TextChannel mafiaChannel)
    {
        currentState = GameState.PREPARATION;
        mafiaChannel.sendMessage("Mafia preparation! Write !participate to enter the game.\n" +
                                      "Write !Mafia_start to start the game.").queue();
    }

    public void addParticipant(User participant)
    {

    }
}
