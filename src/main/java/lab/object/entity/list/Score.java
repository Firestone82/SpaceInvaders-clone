package lab.object.entity.list;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.enums.Constants;
import lab.enums.Source;
import lab.interfaces.Drawable;
import lab.object.Game;
import lab.object.entity.Entity;
import lab.object.other.Utils;

public class Score extends Entity implements Drawable {
    private int points = 0;

    public Score(Game game, Source source) {
        super(game, source, Utils.getPoint(Constants.GLOBAL_BORDER, Constants.GLOBAL_BORDER + Constants.FONT_SIZE / 2));
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();

        gc.save();
        gc.setFont(Font.font("ArcadeClassic", Constants.FONT_SIZE));

        gc.setFill(Color.GRAY);
        gc.fillText("Score ", position.getX(), position.getY());

        gc.setFill(Color.rgb(0,255,33));
        gc.fillText(""+ points, position.getX() + 95, position.getY());

        gc.restore();
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }
}
