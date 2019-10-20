package com.clown.games.mafia.roles.discord;

import com.clown.games.mafia.roles.Player;
import net.dv8tion.jda.api.entities.User;

public interface DiscordPlayer extends Player
{
    User getUser();
}
