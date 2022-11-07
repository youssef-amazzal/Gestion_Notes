package org.example.services;

import java.sql.SQLException;
import java.sql.Statement;

public class IdGenerator {
    private static int id;
    static {
        String query = "" +
                "SELECT MAX(m1,m2,m3) " +
                "FROM " +
                "   (SELECT MAX(id) m1 FROM Filiere)," +
                "   (SELECT MAX(id) m2 FROM semestre)," +
                "   (SELECT MAX(id) m3 FROM Module);";
        try (Statement statement = DataBaseConnection.getInstance().createStatement()) {
            id = statement.executeQuery(query).getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getId() {
        return ++id;
    }
}
