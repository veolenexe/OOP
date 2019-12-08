package com.clown.games.mafia;

import com.clown.games.mafia.db.MafiaMySQLDatabase;
import com.clown.games.mafia.discord.DiscordMafiaBot;
import com.clown.games.mafia.discord.DiscordMessageListener;
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
        DiscordMessageListener discordMessageListener = new DiscordMessageListener();
        MafiaMySQLDatabase database = new MafiaMySQLDatabase();
        DiscordMafiaBot discordMafiaBot = new DiscordMafiaBot(discordMessageListener, database);
        builder.addEventListeners(discordMafiaBot.getMessageListener());
        builder.build();
    }
}
