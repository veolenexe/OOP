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

    }
}
