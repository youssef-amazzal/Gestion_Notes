package org.example.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection connection = null;

    private DataBaseConnection () {
        try {
            String path = System.getProperty("user.home") + "\\documents\\gestionNotes.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstance() {
        if (connection == null) {
            new DataBaseConnection();
        }
        return connection;
    }

}
