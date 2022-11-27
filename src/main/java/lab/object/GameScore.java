package lab.object;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lab.object.other.Utils;

public class GameScore {
    private final SimpleStringProperty playerName;
    private final SimpleIntegerProperty score;
    private final SimpleLongProperty time;

    public GameScore(String playerName, int score, long time) {
        this.playerName = new SimpleStringProperty(playerName);
        this.score = new SimpleIntegerProperty(score);
        this.time = new SimpleLongProperty(time);
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public long getRawTime() {
        return time.get();
    }

    public String getTime() {
        return Utils.getTimerString(time.get()) +"";
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    @Override
    public String toString() {
        return playerName +" - "+ score;
    }
}
