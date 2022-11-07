package org.example.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptRunner {

    public static void runScript(String scriptFile) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ScriptRunner.class.getResourceAsStream(scriptFile)))) {

            Statement statement = DataBaseConnection.getInstance().createStatement();

            String line;
            StringBuilder sqlQuery = new StringBuilder();

            while ((line = reader.readLine()) != null ) {
                sqlQuery.append(line);

                if (line.contains(";")) {
                    statement.addBatch(sqlQuery.toString());
                    sqlQuery.setLength(0);
                }
            }

            statement.executeBatch();
        }
        catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}
