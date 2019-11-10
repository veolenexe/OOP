package com.clown.games.mafia.discord;

import com.clown.games.mafia.Game;
import com.clown.games.mafia.ICommand;
import com.clown.games.mafia.messaging.IMessageListener;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.IMove;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Optional;

public class DiscordMafiaBot
{
    private HashMap<String, Game> games;
    private HashMap<String, ICommand> preparationCommandHandlers;
    private HashMap<String, ICommand> dayCommandHandlers;
    private HashMap<String, ICommand> nightCommandHandlers;
    private DiscordMessageListener messageListener;

    public DiscordMafiaBot(DiscordMessageListener messageListener)
    {
        this.messageListener = messageListener;
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
    }

    private void onMessageReceived(String message)
    {
        String channelId = messageListener.getTextChannel().getId();

        if ("!Mafia".equalsIgnoreCase(message))
        {
            beginMafiaInASeparateChannel(channelId);
            return;
        }

        Game game = games.get(channelId);
        User user = messageListener.getMessageAuthor();

        if ("!help".equalsIgnoreCase(message))
        {
            printHelp(game);
        }

        handleCommands(message, game, user);
    }

    private void handleCommands(String message, Game game, User user)
    {
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
        Game game;
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
    
    private void handleJoinMafia(String message, Game game, User user)
    {
        if (!game.isPlayerParticipant(user.getId()))//Убрать для теста.
        {
            IPlayer newPlayer = new DiscordPlayer(user, game.getCurrentPlayersCount() + 1);
            game.addParticipant(newPlayer);
        }
    }

    private void handleMafiaStart(String message, Game game, User user)
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

    private void handleVote(String message, Game game, User user)
    {
        String playerNumber = message.substring(7, 8);
        String votedPlayerId = user.getId();
        game.makeAVote(playerNumber, votedPlayerId);
    }

    private void handlePrivateMessage(String message, Game game, User user)
    {
        String[] messageData = message.substring(2).split(" ");

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
                player.setMadeMove(true);
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

    private void printHelp(Game game)
    {
        game.sendMessage("No one will help you!");
    }

    public IMessageListener getMessageListener()
    {
        return messageListener;
    }
}
