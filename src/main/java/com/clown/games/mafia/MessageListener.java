package com.clown.games.mafia;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{
    private Game game;
    MessageListener(Game game)
    {
        this.game = game;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {

        String messageContent = event.getMessage().getContentRaw();

        if (event.getAuthor().isBot())
        {
            return;
        }
        if ("!help".equals(messageContent))
        {
            printHelp(event);
        }

        if ("!com.clown.games.mafia.roles.Mafia".equals(messageContent) || "!mafia".equals(messageContent))
        {
            game.setChannel(event.getChannel());
            game.prepareForGame();
        }

        switch (game.getCurrentGameState())
        {
            case PREPARATION:
            {
                switch (messageContent)
                {
                    case "!join mafia":
                    {
                        if (!game.isPlayerParticipant(event.getAuthor().getName()))//Убрать для теста.
                        {
                            game.addParticipant(event.getAuthor());
                        }
                        break;
                    }
                    case "!com.clown.games.mafia.roles.Mafia start":
                    {
                        if (game.getCurrentPlayersCount() < 5)
                        {
                            game.getChannel().sendMessage("недостаточное кол-во игроков," +
                                    " необходимо 5 или больше людей в игре: " +
                                    game.getCurrentPlayersCount()).queue();
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
                if (messageContent.startsWith("!vote "))
                {
                    String playerName = messageContent.substring(7);
                    String votedPlayerName = event.getAuthor().getName();
                    game.makeAVote(playerName, votedPlayerName);
                }
                break;
            }
        }
    }

    private void printHelp(GuildMessageReceivedEvent event)
    {

    }
}
