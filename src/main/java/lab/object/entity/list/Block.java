package lab.object.entity.list;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lab.enums.Constants;
import lab.enums.Source;
import lab.interfaces.Drawable;
import lab.object.Game;
import lab.object.entity.Entity;
import lab.object.other.Utils;

public class Block extends Entity implements Drawable {
    private final int maxHealth = 15;
    private int health = maxHealth;

    public Block(Game game, Source source, double x, double y) {
        super(game, source, Utils.getPoint(x, y));
        this.active = true;
        this.hittable = true;
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();

        if (explode > 0) {
            explode--;

            gc.drawImage(Source.EXPLOSION.getImage(),position.getX() - 3,position.getY() - 3,source.getWidth() + 6,source.getHeight() + 6);
        } else if (active) {
            gc.drawImage(source.getImage(), position.getX(), position.getY(), source.getWidth(), source.getHeight());

            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("ArcadeClassic", Constants.FONT_SIZE));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(""+ health, position.getX() + 32, position.getY() + 30);
        }

        gc.restore();
    }

    public void execute() {
        health--;

        if (health <= 0) {
            super.setActive(false);
        }
    }

    public void reset() {
        active = true;
        health = maxHealth;
    }
}
