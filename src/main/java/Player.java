import net.dv8tion.jda.api.entities.User;

public class Player
{
    private User user;
    private Role role;
    private boolean isDead;

    public Player(User user,  Role role)
    {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
