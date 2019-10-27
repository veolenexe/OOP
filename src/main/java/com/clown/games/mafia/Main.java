package com.clown.games.mafia;

import com.clown.games.mafia.discord.DiscordMafiaBot;
import com.clown.games.mafia.discord.DiscordMessageListener;
import com.clown.games.mafia.discord.DiscordMessageSender;
import com.clown.games.mafia.discord.DiscordPrivateMessageListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main
{
    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "";
        builder.setToken(token);
        DiscordMessageSender discordMessageSender = new DiscordMessageSender();
        DiscordMessageListener discordMessageListener = new DiscordMessageListener();
        DiscordMafiaBot discordMafiaBot = new DiscordMafiaBot(discordMessageSender, discordMessageListener);
        builder.addEventListeners(discordMafiaBot.getMessageListener());
        builder.build();
    }
}
