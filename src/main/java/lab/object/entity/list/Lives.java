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

public class Lives extends Entity implements Drawable {
    private final int defaultLives = 3;
    private int lives = defaultLives;

    public Lives(Game game, Source source) {
        super(game, source, Utils.getPoint(230,Constants.GLOBAL_BORDER + Constants.FONT_SIZE / 2));
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();

        gc.save();
        gc.setFont(Font.font("ArcadeClassic", Constants.FONT_SIZE));

        gc.setFill(Color.GRAY);
        gc.fillText("Lives ", position.getX(), position.getY());

        for (int i = 0; i < lives; i++) {
            game.getController().getCanvas().getGraphicsContext2D().drawImage(Source.SHIP.getImage(),position.getX() + 100 + (i * (Source.SHIP.getWidth() + 10)), position.getY() - Source.SHIP.getHeight(),Source.SHIP.getWidth(), Source.SHIP.getHeight());
        }

        gc.restore();
    }

    public void reset() {
        lives = defaultLives;
    }

    public int getLives() {
        return lives;
    }

    public void removeLive() {
        this.lives--;
    }
}
