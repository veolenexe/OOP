package com.clown.games.mafia.db;

import com.clown.games.mafia.player.IPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MafiaMySQLDatabase extends MySQLDatabase
{
    public void addNewPlayer(IPlayer player){
        if(isPlayerInDatabase(player))
            throw new IllegalArgumentException("Player is already in database.");
        String id = player.getPlayerID();
        String name = player.getPlayerName();
        String query = String.format("INSERT playersInfo(UID, Nickname, MafiaWin, CitizenWin) VALUES ('%s', '%s', 0, 0)", id, name);
        executeUpdate(query);
    }

    public void addMafiaWin(IPlayer player){
        if(!isPlayerInDatabase(player))
            throw new IllegalArgumentException("Player is not in database!");
        String id = player.getPlayerID();
        String query = String.format("UPDATE playersInfo SET MafiaWin = MafiaWin + 1 WHERE UID = '%s'", id);
        executeUpdate(query);
    }

    public void addCitizenWin(IPlayer player){
        if(!isPlayerInDatabase(player))
            throw new IllegalArgumentException("Player is not in database!");
        String id = player.getPlayerID();
        String query = String.format("UPDATE playersInfo SET CitizenWin = CitizenWin + 1 WHERE UID = '%s'", id);
        executeUpdate(query);
    }

    public void updatePlayerName(IPlayer player){
        if(!isPlayerInDatabase(player))
            throw new IllegalArgumentException("Player is not in database!");
        String name = player.getPlayerName();
        String id = player.getPlayerID();
        String query = String.format("SELECT * FROM playersInfo WHERE UID = '%s'", id);
        ResultSet result = executeQuery(query);
        try {
            result.next();
            String dbName = result.getString("Nickname");
            if(!dbName.equals(name))
            {
                String updateQuery = String.format("UPDATE playersInfo SET Nickname = '%s' WHERE UID = '%s'", name, id);
                executeUpdate(updateQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerInDatabase(IPlayer player){
        String  id = player.getPlayerID();
        String query = String.format("SELECT * FROM playersInfo WHERE UID = '%s'", id);
        ResultSet result = executeQuery(query);
        try {
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void connectToDb(){
        Properties props = new Properties();
        Path dir = Paths.get("src/main/resources/db");
        Path path = dir.resolve("database.properties");
        try(InputStream in = Files.newInputStream(path)){
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        establishConnection(url, username, password);
        System.out.println(String.format("Successfully connected to %s", url));
    }
}
