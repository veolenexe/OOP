package com.clown.games.mafia;

import com.clown.games.mafia.db.MafiaMySQLDatabase;
import com.clown.games.mafia.discord.DiscordMafiaBot;
import com.clown.games.mafia.discord.DiscordMessageListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main
{
    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = getToken();
        builder.setToken(token);
        DiscordMessageListener discordMessageListener = new DiscordMessageListener();
        MafiaMySQLDatabase database = new MafiaMySQLDatabase();
        DiscordMafiaBot discordMafiaBot = new DiscordMafiaBot(discordMessageListener, database);
        builder.addEventListeners(discordMafiaBot.getMessageListener());
        builder.build();
    }

    private static String getToken()
    {
        Properties props = new Properties();
        Path dir = Paths.get("src/main/resources");
        Path path = dir.resolve("token.properties");
        try(InputStream in = Files.newInputStream(path)){
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("token");
    }
}
