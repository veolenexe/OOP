package com.clown.games.mafia.roles.discord;

import com.clown.games.mafia.roles.BasePlayer;
import com.clown.games.mafia.roles.Player;
import com.clown.games.mafia.roles.Role;
import net.dv8tion.jda.api.entities.User;

public abstract class BaseDiscordPlayer extends BasePlayer implements DiscordPlayer
{
    protected User user;

    public BaseDiscordPlayer(User user)
    {
        super(user.getName(), Role.CITIZEN);
        this.user = user;
    }

    public BaseDiscordPlayer(DiscordPlayer player)
    {
        super(player.getPlayerName(), Role.CITIZEN);
        user = player.getUser();
    }

    public BaseDiscordPlayer(Player player)
    {
        super(player.getPlayerName(), player.getRole());
        BaseDiscordPlayer discordPlayer = (BaseDiscordPlayer) player;
        user = discordPlayer.getUser();
    }

    @Override
    public User getUser()
    {
        return user;
    }

    @Override
    public void sendPrivateMessage(String message)
    {
        user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
    }
}
