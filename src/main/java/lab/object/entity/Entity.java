package lab.object.entity;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import lab.enums.Source;
import lab.interfaces.Drawable;
import lab.object.Game;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity implements Drawable {
    protected final Game game;

    protected final Source source;
    protected final Point2D defaultPosition;
    protected Point2D position;

    protected boolean active = false;
    protected boolean hittable = false;
    protected long explode = 0L;

    public Entity(Game game, Source source, Point2D position) {
        this.game = game;
        this.source = source;
        this.defaultPosition = position;
        this.position = defaultPosition;
    }

    public void simulate(double deltaT) {
        // NOTHING
    }

    public boolean isDisabled() {
        return !active;
    }

    public boolean isHittable() {
        return hittable;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public void setActive(boolean active) {
        this.active = active;

        if (!active && isHittable()) {
            explode();
        }
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), source.getWidth(), source.getHeight());
    }

    public boolean overlaps(Entity entity) {
        return getBoundingBox().intersects(entity.getBoundingBox()) && active;
    }

    public Source getSource() {
        return source;
    }

    public void explode() {
        explode = 18;

        /*
        new Timer().schedule(new TimerTask() {
            long units = 0;

            @Override
            public void run() {
                game.getController().getCanvas().getGraphicsContext2D().drawImage(Source.EXPLOSION.getImage(),position.getX() - 3,position.getY() - 3,source.getWidth() + 6,source.getHeight() + 6);
                units++;

                if (units > 150) {
                    cancel();
                }
            }
        },0,1);
         */
    }
}
