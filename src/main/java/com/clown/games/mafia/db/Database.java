package com.clown.games.mafia.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    void establishConnection(String url, String username, String password) throws SQLException;
    void executeUpdate(String sqlCommand) throws SQLException;
    ResultSet executeQuery(String sqlCommand) throws SQLException;
}
