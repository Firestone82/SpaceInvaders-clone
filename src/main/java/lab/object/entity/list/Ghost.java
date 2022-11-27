package lab.object.entity.list;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import lab.enums.Constants;
import lab.enums.Direction;
import lab.enums.Source;
import lab.interfaces.Drawable;
import lab.object.Game;
import lab.object.entity.Entity;
import lab.object.other.Utils;

public class Ghost extends Entity implements Drawable {
    private Direction direction = Direction.RIGHT;

    private final int num;
    private boolean id = true;
    private long ticks = 0;

    public Ghost(Game game, Source source, double x, double y, int num) {
        super(game, source, Utils.getPoint(x, y));
        this.active = true;
        this.hittable = true;
        this.num = num;
    }

    @Override
    public void draw() {
        GraphicsContext gc = game.getController().getCanvas().getGraphicsContext2D();
        gc.save();

        if (explode > 0) {
            explode--;

            gc.drawImage(Source.EXPLOSION.getImage(),position.getX() - 3,position.getY() - 3,source.getWidth() + 6,source.getHeight() + 6);
        } else if (active) {
            gc.drawImage(source.getImage(id ? 1 : 0), position.getX(), position.getY(), source.getWidth(), source.getHeight());

            //gc.setFill(Color.RED);
            //gc.fillText(""+ num, position.getX(), position.getY(),15);

            if (ticks++ > 15) {
                id = !id;
                ticks = 0;
            }
        }

        gc.restore();
    }

    @Override
    public void simulate(double deltaT) {
        if (active) {
            int totalGhostCount = (int) game.getEntities().stream().filter(en -> en instanceof Ghost ghost).count();
            int aliveGhostCount = (int) game.getEntities().stream().filter(en -> en instanceof Ghost ghost && !ghost.isDisabled()).count();

            double inc = ((System.currentTimeMillis() - game.getLevelTime()) / 5000F) + (totalGhostCount - aliveGhostCount) / 100F;

            switch (direction) {
                case LEFT -> {
                    position = position.subtract((5 + inc) * deltaT, 0);

                    if (position.getX() < 0) {
                        position = new Point2D(0, position.getY());

                        for (Ghost ghost : game.getGhosts()) {
                            ghost.setDirection(Direction.RIGHT);
                            ghost.setPosition(ghost.getPosition().add(0,10));
                        }
                    }
                }

                case RIGHT -> {
                    position = position.add((5 + inc) * deltaT, 0);

                    if (position.getX() + source.getWidth() > Constants.GAME_WIDTH) {
                        position = new Point2D(Constants.GAME_WIDTH - source.getWidth(), position.getY());

                        for (Ghost ghost : game.getGhosts()) {
                            ghost.setDirection(Direction.LEFT);
                            ghost.setPosition(ghost.getPosition().add(0,10));
                        }
                    }
                }
            }

            if (Utils.getRandom(((totalGhostCount - aliveGhostCount) * 50) + (100 * (int) ((System.currentTimeMillis() - game.getLevelTime()) / 5000F)), totalGhostCount * 100) == totalGhostCount * 100) {
                game.getEntities().add(new AlienBullet(game, Source.ALIEN_BULLET, new Point2D(position.getX(), position.getY())));
            }
        }
    }

    public int getPoints() {
        return switch (source) {
            case GHOST_A -> 40;
            case GHOST_B -> 20;
            case GHOST_C -> 10;
            default -> 0;
        };
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
