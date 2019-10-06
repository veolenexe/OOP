import net.dv8tion.jda.api.entities.User;

public class Player
{
    private User user;
    private Role role;

    private String playerName;
    private boolean isDead;

    private boolean hasVoted;
    private String votedPlayerName;

    public Player(User user,  Role role)
    {
        this.user = user;
        this.role = role;
        playerName = user.getName();
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public boolean getHasVoted()
    {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public String getVotedPlayerName()
    {
        return votedPlayerName;
    }

    public void setVotedPlayerName(String votedPlayerName)
    {
        this.votedPlayerName = votedPlayerName;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void sendPrivateMessage(String text){
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(text).queue();
        });
    }
}
