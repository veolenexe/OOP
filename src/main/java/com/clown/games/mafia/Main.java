package com.clown.games.mafia;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main
{
    private static Game game = new Game();

    public static void main(String[] args) throws LoginException
    {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NjI3Nzg5NTkwMzI5MTYzNzc2.XZN87A.3yiiyCD52bKZ6mP0rBc8KR2Ujn4";
        builder.setToken(token);
        builder.addEventListeners(new MessageListener(game));
        builder.build();
    }
}
