package lab.object.entity.list;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lab.enums.Constants;
import lab.enums.Source;
import lab.interfaces.Drawable;
import lab.object.Game;
import lab.object.entity.Entity;
import lab.object.other.Utils;

public class ShipBullet extends Entity implements Drawable {

    public ShipBullet(Game game, Source source) {
        super(game, source, Utils.getPoint(0,0));
    }

    @Override
    public void draw() {
        if (active) {
            GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();

            gc.save();
            gc.setFill(Color.rgb(0,255,33));

            gc.fillRect(position.getX(), position.getY(),3,16);

            gc.restore();
        }
    }

    @Override
    public void simulate(double deltaT) {
        if (active) {
            position = position.add(new Point2D(0,-1).multiply(350 * deltaT));

            if (position.getY() < Constants.SCORE_HEIGHT) {
                reset();
            }
        }
    }

    public void execute() {
        position = game.getObject(Source.SHIP).getPosition().add(Source.SHIP.getWidth() / 2 - 1, -Source.SHIP.getHeight());
        active = true;
    }

    public void reset() {
        active = false;
        position = defaultPosition;
    }
}
