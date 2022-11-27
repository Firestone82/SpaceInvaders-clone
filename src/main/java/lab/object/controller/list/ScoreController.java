package lab.object.controller.list;

import lab.App;
import lab.object.GameScore;
import lab.object.controller.MainController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;

public class ScoreController {
    private final App app;
    private final MainController mainController;

    private final HashMap<String, GameScore> list = new HashMap<>();

    public ScoreController(App app, MainController mainController) {
        this.app = app;
        this.mainController = mainController;
    }

    public void loadScore() {
        try (Connection connection = mainController.getDatabaseController().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Scores");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String player = rs.getString("player");
                    int score = rs.getInt("score");
                    long time = rs.getLong("timeMilis");

                    saveScore(player, new GameScore(player, score, time));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveScore() {
        System.out.println("save db");

        try (Connection connection = mainController.getDatabaseController().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("REPLACE INTO Scores (player, score, timeMilis) VALUES (?, ?, ?)");

            for (GameScore score : list.values()) {
                ps.setString(1, score.getPlayerName());
                ps.setInt(2, score.getScore());
                ps.setLong(3, score.getRawTime());

                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveScore(String player, GameScore score) {
        System.out.println("save "+ player);

        if (list.containsKey(player)) {
            GameScore oldScore = list.get(player);

            if (oldScore.getScore() < score.getScore()) {
                list.put(player, score);
            }
        } else {
            list.put(player, score);
        }
    }

    public Collection<GameScore> getScoreList() {
        return list.values();
    }
}
