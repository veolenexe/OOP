package com.clown.games.mafia.db;

import java.sql.*;

public class MySQLDatabase implements Database{
    private Statement statement;
    @Override
    public void establishConnection(String url, String username, String password){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeUpdate(String sqlCommand){
        try {
            statement.executeUpdate(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String sqlCommand){
        try {
            return statement.executeQuery(sqlCommand);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
