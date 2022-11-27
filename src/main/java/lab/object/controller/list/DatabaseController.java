package lab.object.controller.list;

import lab.App;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseController {
    private final App app;

    private final File file;

    public DatabaseController(App app) {
        this.app = app;
        this.file = new File(app.getResourcePath() +"/database.db");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (Connection connection = getConnection()) {
            connection.createStatement().executeUpdate("" +
                    "CREATE TABLE IF NOT EXISTS Scores (" +
                    "   id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "   player VARCHAR(255), " +
                    "   score INT," +
                    "   timeMilis LONG)"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
