import net.dv8tion.jda.api.entities.User;

public class Citizen implements Player {
    protected Role role;
    private User user;
    private String playerName;
    private boolean isDead;
    private boolean hasVoted;
    private String votedPlayerName;

    public Citizen(User user) {
        role = Role.CITIZEN;
        this.user = user;
        playerName = user.getName();
    }
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public boolean getIsDead() {
        return isDead;
    }

    @Override
    public void setIsDead(boolean isDead) {
        this.isDead=isDead;
    }


    @Override
    public boolean getHasVoted() {
        return hasVoted;
    }

    @Override
    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    @Override
    public String getVotedPlayerName() {
        return votedPlayerName;
    }

    @Override
    public void setVotedPlayerName(String votedPlayerName) {
        this.votedPlayerName = votedPlayerName;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void sendPrivateMessage(String text) {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(text).queue();
        });
    }
}
