package com.clown.games.mafia.discord;

import com.clown.games.mafia.Game;
import com.clown.games.mafia.ICommand;
import com.clown.games.mafia.IGame;
import com.clown.games.mafia.db.MafiaMySQLDatabase;
import com.clown.games.mafia.messaging.IMessageListener;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.IMove;
import com.clown.games.mafia.roles.Roles;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DiscordMafiaBot
{
    private Map<String, IGame> games;
    private Map<String, ICommand> preparationCommandHandlers;
    private Map<String, ICommand> dayCommandHandlers;
    private Map<String, ICommand> nightCommandHandlers;
    private DiscordMessageListener messageListener;
    private MafiaMySQLDatabase database;

    public DiscordMafiaBot(DiscordMessageListener messageListener, MafiaMySQLDatabase database)
    {
        this.messageListener = messageListener;
        this.database = database;
        messageListener.setReceivingFunction(message ->
        {
            onMessageReceived(message);
            return true;
        });
        games = new HashMap<>();
        preparationCommandHandlers = new HashMap<>();
        preparationCommandHandlers.put("!join mafia", this::handleJoinMafia);
        preparationCommandHandlers.put("!Mafia start", this::handleMafiaStart);
        dayCommandHandlers = new HashMap<>();
        dayCommandHandlers.put("!vote", this::handleVote);
        nightCommandHandlers = new HashMap<>();
        nightCommandHandlers.put("P:", this::handlePrivateMessage);
        database.connectToDb();
    }

    private void onMessageReceived(String message)
    {
        String channelId = messageListener.getTextChannel().getId();

        if ("!Mafia".equalsIgnoreCase(message))
        {
            beginMafiaInASeparateChannel(channelId);
            return;
        }

        IGame game = games.get(channelId);
        User user = messageListener.getMessageAuthor();

        if ("!help".equalsIgnoreCase(message))
        {
            printHelp(game);
        }

        if (game != null)
        {
            handleCommands(message, game, user);
            if(game.getHasEnded())
            {
                FillDatabase(game);
                games.remove(channelId);
            }
        }
    }

    private void FillDatabase(IGame game)
    {
        if(game.getMafiaWon())
        {
            List<IPlayer> mafiaPlayers = game.getParticipantsByRole(Roles.MAFIA);
            for (IPlayer player : mafiaPlayers)
                database.addMafiaWin(player);
            return;
        }
        List<IPlayer> notMafiaPlayers = game.getParticipantsExcludingRole(Roles.MAFIA);
        for (IPlayer player : notMafiaPlayers)
            database.addCitizenWin(player);
    }

    private void handleCommands(String message, IGame game, User user)
    {
        if("!stats".equalsIgnoreCase(message))
        {
            printPlayersStatistic(game);
            return;
        }

        if("!mystats".equalsIgnoreCase(message))
        {
            printPlayerStatistic(game, user);
            return;
        }

        switch (game.getCurrentGameState())
        {
            case PREPARATION:
            {
                 preparationCommandHandlers.get(message).handleCommand(message, game, user);
                break;
            }
            case DAY:
            {
                if (message.startsWith("!vote "))
                {
                    dayCommandHandlers.get("!vote").handleCommand(message, game, user);
                }
                break;
            }
            case NIGHT:
            {
                if (message.startsWith("P:"))
                {
                    nightCommandHandlers.get("P:").handleCommand(message, game, user);
                }
                break;
            }
        }
    }

    private void beginMafiaInASeparateChannel(String channelId)
    {
        IGame game;
        if(games.containsKey(channelId))
        {
            game = games.get(channelId);
        }
        else
        {
            game = new Game();
            games.put(channelId, game);
        }
        DiscordMessageSender gameMessageSender = new DiscordMessageSender();
        gameMessageSender.setTextChannel(messageListener.getTextChannel());
        game.setMessageSender(gameMessageSender);
        game.prepareForGame();
    }
    
    private void handleJoinMafia(String message, IGame game, User user)
    {
        if (!game.isPlayerParticipant(user.getId()))//Убрать для теста.
        {
            IPlayer newPlayer = new DiscordPlayer(user, game.getCurrentPlayersCount() + 1);
            if(!database.isPlayerInDatabase(newPlayer))
            {
                database.addNewPlayer(newPlayer);
            }
            else
            {
                database.updatePlayerName(newPlayer);
            }
            game.addParticipant(newPlayer);
        }
    }

    private void handleMafiaStart(String message, IGame game, User user)
    {
        if (game.getCurrentPlayersCount() < 5)
        {
            game.sendMessage("недостаточное кол-во игроков," +
                    " необходимо 5 или больше людей в игре: " +
                    game.getCurrentPlayersCount());
        }
        else
        {
            game.startGame();
        }
    }

    private void handleVote(String message, IGame game, User user)
    {
        String playerNumber = message.split(" ")[1];
        String votedPlayerId = user.getId();
        game.makeAVote(playerNumber, votedPlayerId);
    }

    private void handlePrivateMessage(String message, IGame game, User user)
    {
        String[] messageData = message.substring(3).split(" ");

        String playerId = messageData[0];
        int playerNumberToMakeMoveOn = Integer.parseInt(messageData[1]);
        Optional<IPlayer> playerOptional = game.getPlayerByID(playerId);
        Optional<IPlayer> playerToMakeMoveOnOptional = game.getPlayerByNumber(playerNumberToMakeMoveOn);
        if (playerOptional.isPresent())
        {
            IPlayer player = playerOptional.get();
            if (playerToMakeMoveOnOptional.isPresent())
            {
                if (player.getMadeMove())
                {
                    player.sendPrivateMessage("You cannot move twice!");
                    return;
                }
                IPlayer playerToMakeMoveOn = playerToMakeMoveOnOptional.get();
                IMove move = player.makeMove(playerToMakeMoveOn);
                game.addMove(player.getPlayerMovePriority(), move);
            }
            else
            {
                player.sendPrivateMessage("Wrong player number");
            }

        }
        else
        {
            game.sendMessage("How could this happen?");
        }
    }

    private void printPlayerStatistic(IGame game, User user)
    {
        String statistics = database.getPlayerInfo(user.getId());
        user.openPrivateChannel().queue(channel -> channel.sendMessage(statistics).queue());;
    }

    private void printPlayersStatistic(IGame game)
    {
        String statistics = database.getPlayersInfo(game.getParticipants());
        game.sendMessage(statistics);
    }

    private void printHelp(IGame game)
    {
        game.sendMessage("No one will help you!");
    }

    public IMessageListener getMessageListener()
    {
        return messageListener;
    }
}
