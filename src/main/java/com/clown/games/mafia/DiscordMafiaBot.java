package com.clown.games.mafia;

import com.clown.games.mafia.roles.IPlayer;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

class DiscordMafiaBot
{
    private Game game;
    private DiscordMessageSender messageSender;
    private DiscordMessageListener messageListener;
    private Map<IPlayer, User> discordUsers;

    DiscordMafiaBot(DiscordMessageSender messageSender,
                    DiscordMessageListener messageListener)
    {
        this.messageSender = messageSender;
        messageSender.setDiscordMafiaBot(this);
        this.messageListener = messageListener;
        messageListener.setReceivingFunction(message ->
        {
            onMessageReceived(message);
            return true;
        });
        game = new Game();
        game.setMessageSender(messageSender);
        discordUsers = new HashMap<>();
    }

    private void onMessageReceived(String message)
    {
        if ("!help".equals(message))
        {
            printHelp();
        }
        if ("!Mafia".equalsIgnoreCase(message))
        {
            messageSender.setTextChannel(messageListener.getTextChannel());
        }
        switch (game.getCurrentGameState())
        {
            case PREPARATION:
            {
                switch (message)
                {
                    case "!join mafia":
                    {
                        if (!game.isPlayerParticipant(messageListener.getMessageAuthor().getName()))//Убрать для теста.
                        {
                            IPlayer newPlayer = new DiscordPlayer(messageListener.getMessageAuthor());
                            discordUsers.put(newPlayer, messageListener.getMessageAuthor());
                            game.addParticipant(newPlayer);
                        }
                        break;
                    }
                    case "!Mafia start":
                    {
                        if (game.getCurrentPlayersCount() < 5)
                        {
                            messageSender.sendMessage("недостаточное кол-во игроков," +
                                    " необходимо 5 или больше людей в игре: " +
                                    game.getCurrentPlayersCount());
                        }
                        else
                        {
                            game.startGame();
                        }
                        break;
                    }
                }
                break;
            }
            case DAY:
            {
                if (message.startsWith("!vote "))
                {
                    String playerName = message.substring(7);
                    String votedPlayerName = messageListener.getMessageAuthor().getName();
                    game.makeAVote(playerName, votedPlayerName);
                }
                break;
            }
        }
    }

    private void printHelp()
    {
        messageSender.sendMessage("No one will help you!");
    }

    IMessageListener getMessageListener()
    {
        return messageListener;
    }

    User getUserByPlayer(IPlayer player)
    {
        return discordUsers.getOrDefault(player, null);
    }
}
