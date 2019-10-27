package com.clown.games.mafia.discord;

import com.clown.games.mafia.Game;
import com.clown.games.mafia.messaging.IMessageListener;
import com.clown.games.mafia.player.IPlayer;
import com.clown.games.mafia.roles.IMove;
import net.dv8tion.jda.api.entities.User;

import java.util.Optional;

public class DiscordMafiaBot
{
    private Game game;
    private DiscordMessageSender messageSender;
    private DiscordMessageListener messageListener;

    public DiscordMafiaBot(DiscordMessageSender messageSender,
                           DiscordMessageListener messageListener)
    {
        this.messageSender = messageSender;
        this.messageListener = messageListener;
        messageListener.setReceivingFunction(message ->
        {
            onMessageReceived(message);
            return true;
        });
        game = new Game();
        game.setMessageSender(messageSender);
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
            game.prepareForGame();
        }
        User user = messageListener.getMessageAuthor();
        switch (game.getCurrentGameState())
        {
            case PREPARATION:
            {
                switch (message)
                {
                    case "!join mafia":
                    {
                        if (!game.isPlayerParticipant(user.getId()))//Убрать для теста.
                        {
                            IPlayer newPlayer = new DiscordPlayer(user, game.getCurrentPlayersCount() + 1);
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
                    String playerNumber = message.substring(7, 8);
                    String votedPlayerId = user.getId();
                    game.makeAVote(playerNumber, votedPlayerId);
                }
                break;
            }
            case NIGHT:
            {
                if (message.startsWith("P:"))
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
                        messageSender.sendMessage("How could this happen?");
                    }
                }
                break;
            }
        }
    }

    private void printHelp()
    {
        messageSender.sendMessage("No one will help you!");
    }

    public IMessageListener getMessageListener()
    {
        return messageListener;
    }
}
