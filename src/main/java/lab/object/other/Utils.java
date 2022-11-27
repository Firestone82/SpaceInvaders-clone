package lab.object.other;

import javafx.geometry.Point2D;
import lab.enums.Constants;

import java.util.Random;

public class Utils {

    public static Point2D getPoint(double x, double y) {
        return new Point2D(x, y);
    }

    public static Point2D getPoint(Point2D point) {
        return new Point2D(point.getX(),Constants.GAME_HEIGHT - point.getY());
    }

    public static String getPads(int pos) {
        if (pos < 10) {
            return "     "+ pos;
        } else {
            return ""+ pos;
        }
    }

    public static int getRandom(int min, int max) {
        return new Random().nextInt(min,max + 1);
    }

    public static String getTimerString(long millis) {
        String s = "";
        Long amount = 0L;

        amount /= 60000L;
        millis %= 60000L;
        s = s + ((amount < 10L) ? "0" : "") + amount + ":";

        amount = millis / 1000L;
        millis = millis % 1000L;
        s = s + ((amount < 10L) ? "0" : "") + amount + ":";

        s = s + ((millis < 100L) ? ("0" + ((millis < 10L) ? "0" : "")) : "") + millis;
        return s;
    }

}
