package com.clown.games.mafia;

import com.clown.games.mafia.discord.DiscordMafiaBot;
import com.clown.games.mafia.discord.DiscordMessageListener;
import com.clown.games.mafia.discord.DiscordMessageSender;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main
{
    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NjI3Nzg5NTkwMzI5MTYzNzc2.XbWl5A._-OprS9cwg0ep3KwRo9Ir_HEPyk";
        builder.setToken(token);
        DiscordMessageListener discordMessageListener = new DiscordMessageListener();
        DiscordMafiaBot discordMafiaBot = new DiscordMafiaBot(discordMessageListener);
        builder.addEventListeners(discordMafiaBot.getMessageListener());
        builder.build();
    }
}
