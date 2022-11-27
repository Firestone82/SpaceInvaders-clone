package lab.object.entity.list;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import lab.enums.Constants;
import lab.enums.Direction;
import lab.enums.GameState;
import lab.enums.Source;
import lab.interfaces.Drawable;
import lab.object.Game;
import lab.object.entity.Entity;

public class Ship extends Entity implements Drawable {

    public Ship(Game game, Source source) {
        super(game, source, new Point2D(Constants.GAME_WIDTH / 2 - source.getWidth() / 2,Constants.GAME_HEIGHT - Constants.GLOBAL_BORDER - source.getHeight()));
        this.hittable = true;
        this.active = true;
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();

        if (explode > 0) {
            explode--;

            gc.drawImage(Source.EXPLOSION.getImage(),position.getX() - 3,position.getY() - 3,source.getWidth() + 6,source.getHeight() + 6);
        } else {
            gc.drawImage(source.getImage(), position.getX(), position.getY(), source.getWidth(), source.getHeight());
        }

        gc.restore();
    }

    public void move(Direction direction, double deltaT) {
        if (direction == Direction.LEFT) {
            position = position.add(new Point2D(-1,0).multiply(150 * deltaT));

            if (position.getX() < 0) {
                position = new Point2D(0, position.getY());
            }
        } else if (direction == Direction.RIGHT) {
            position = position.add(new Point2D(1,0).multiply(150 * deltaT));

            if (position.getX() + source.getWidth() > Constants.GAME_WIDTH) {
                position = new Point2D(Constants.GAME_WIDTH - source.getWidth(), position.getY());
            }
        }
    }

    @Override
    public void explode() {
        super.explode();

        Lives lives = (Lives) game.getObject(Source.LIVES);
        if (lives.getLives() == 1) {
            game.getApp().getMainController().changeState(GameState.OVER);
        } else {
            lives.removeLive();
        }
    }
}
