package lab.object;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.App;
import lab.enums.Constants;
import lab.enums.GameState;
import lab.enums.Source;
import lab.object.controller.list.GameController;
import lab.object.entity.Entity;
import lab.object.entity.list.Ghost;
import lab.object.entity.list.*;
import lab.object.other.InputListener;
import lab.object.other.Utils;
import org.xml.sax.InputSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game extends AnimationTimer {
    private final App app;
    private final GameController controller;

    private final ArrayList<Entity> entities = new ArrayList<>();
    private final InputListener inputListener;
    private long lastTime;

    private long startTime = 0;
    private long levelTime = 0;
    private long pauseTime = 0;

    private String userName = "Test";

    public Game(App app, GameController controller) {
        this.app = app;
        this.controller = controller;

        this.inputListener = new InputListener(app,this);

        addEntities();
        addGhosts();
        addBlocks();
    }

    @Override
    public void handle(long now) {
        if (lastTime > 0) {
            double deltaT = (now - lastTime) / 1e9;

            if (app.getMainController().getState() == GameState.PLAYING) {
                inputListener.tick(deltaT);
                for (Entity e : List.copyOf(entities)) { e.simulate(deltaT); }
            }
        }

        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        gc.save();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);

        switch (app.getMainController().getState()) {
            case PLAYING -> {
                if (entities.stream().noneMatch(en -> en instanceof Ghost ghost && !ghost.isDisabled())) {
                    addGhosts();
                    addBlocks();
                    levelTime = System.currentTimeMillis();
                }

                if (Utils.getRandom(0,7500) >= 7500) {
                    UFO ufo = ((UFO) getObject(Source.UFO));

                    if (ufo.isDisabled()) {
                        ufo.execute();
                    }
                }

                for (Entity e : List.copyOf(entities)) {
                    e.draw();

                    for (Ghost ghost : entities.stream().filter(en -> en instanceof Ghost && !en.isDisabled()).map(en -> (Ghost) en).toList()) {
                        if (e.isHittable() && e.overlaps(ghost)) {
                            if (e instanceof Block block) {
                                block.execute();
                                ghost.setActive(false);
                            } else if (e instanceof Ship ship) {
                                ship.explode();
                                ghost.setActive(false);
                            }
                        }
                    }

                    for (ShipBullet shipBullet : entities.stream().filter(en -> en instanceof ShipBullet).map(en -> (ShipBullet) en).toList()) {
                        if (e.isHittable() && e.overlaps(shipBullet)) {
                            shipBullet.setActive(false);
                            shipBullet.reset();

                            if (e instanceof UFO ufo) {
                                ((Score) getObject(Source.SCORE)).addPoints(ufo.getPoints());
                                ufo.setActive(false);
                            } else if (e instanceof Ghost ghost) {
                                ((Score) getObject(Source.SCORE)).addPoints(ghost.getPoints());
                                ghost.setActive(false);
                            } else if (e instanceof Block block) {
                                block.execute();
                            }
                        }
                    }

                    for (AlienBullet alienBullet : entities.stream().filter(en -> en instanceof AlienBullet).map(en -> (AlienBullet) en).toList()) {
                        if (alienBullet.isDisabled()) {
                            entities.remove(alienBullet);
                            continue;
                        }

                        if (e.isHittable() && e.overlaps(alienBullet)) {
                            if (e instanceof Ship ship) {
                                alienBullet.setActive(false);
                                alienBullet.reset();

                                ship.explode();
                            } else if (e instanceof Block block) {
                                alienBullet.setActive(false);
                                alienBullet.reset();

                                block.execute();
                            }
                        }
                    }
                }
            }

            case PAUSED -> {
                gc.drawImage(Source.BLUR.getImage(),0,0);

                gc.setFill(Color.BLACK);
                gc.fillRect(0, Constants.SCORE_HEIGHT + 50, Constants.GAME_WIDTH,Constants.GAME_HEIGHT - 300);

                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("ArcadeClassic",50));
                gc.fillText("GAME  PAUSED",80,200);

                gc.setFill(Color.GRAY);
                gc.setFont(Font.font("ArcadeClassic",25));
                gc.fillText("Press ESC to unpause the game ...",40,250);
            }

            case OVER -> {
                gc.drawImage(Source.BLUR.getImage(),0,0);

                gc.setFill(Color.BLACK);
                gc.fillRect(0, Constants.SCORE_HEIGHT + 50, Constants.GAME_WIDTH,Constants.GAME_HEIGHT - 300);

                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("ArcadeClassic",50));
                gc.fillText("GAME  OVER",100,200);

                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("ArcadeClassic",25));
                gc.fillText("Username ",60,250);

                gc.setFill(Color.YELLOW);
                gc.fillText(userName,190,250);

                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("ArcadeClassic",25));
                gc.fillText("Score ",60,280);

                gc.setFill(Color.YELLOW);
                gc.fillText(((Score) getObject(Source.SCORE)).getPoints() +"",140,280);

                gc.setFill(Color.GRAY);
                gc.setFont(Font.font("ArcadeClassic",25));
                gc.fillText("Press any key to end the game ...",40,400);
            }
        }

        lastTime = now;
        gc.restore();
    }

    @Override
    public void start() {
        super.start();

        if (startTime == 0) {
            startTime = System.currentTimeMillis();
            levelTime = System.currentTimeMillis();
        }

        startTime = startTime + (System.currentTimeMillis() - (pauseTime == 0 ? System.currentTimeMillis() : pauseTime));
        pauseTime = 0;
    }

    public void pause() {
        pauseTime = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        //super.stop();
    }

    public void save() {
        app.getMainController().getScoreController().saveScore(userName, new GameScore(userName, ((Score) getObject(Source.SCORE)).getPoints(),System.currentTimeMillis() - startTime));
        app.getMainController().getScoreController().saveScore();
    }

    public void addEntities() {
        entities.add(new Ship(this, Source.SHIP));
        entities.add(new ShipBullet(this, Source.SHIP_BULLET));
        entities.add(new Score(this, Source.SCORE));
        entities.add(new Lives(this, Source.LIVES));
        entities.add(new UFO(this, Source.UFO));
    }

    public void addGhosts() {
        entities.removeAll(entities.stream().filter(e -> e instanceof Ghost).toList());

        for (int y = 0; y < 5; y++) {
            Source source = null;
            double offset = 0;

            if (y == 0) {
                source = Source.GHOST_A;
                offset = 8;
            } else if (y == 1 || y == 2) {
                source = Source.GHOST_B;
                offset = 2;
            } else {
                source = Source.GHOST_C;
            }

            for (int x = 0; x < 11; x++) {
                double posX = (source.getWidth() * x) + (Constants.ALIEN_SPACER_COLL * x) + ((offset / 2) * x) + ((offset / 2) * x + 1) + offset / 2;
                double posY = Constants.SCORE_HEIGHT + (source.getHeight() * y) + (Constants.ALIEN_SPACER_ROW * y);

                entities.add(new Ghost(this, source,Constants.ALIEN_START.getX() + posX,Constants.ALIEN_START.getY() + posY,y * 11 + x));
            }
        }
    }

    public void addBlocks() {
        entities.removeAll(entities.stream().filter(e -> e instanceof Block).toList());
        Source source = Source.BLOCK;

        for (int x = 0; x < 4; x++) {
            double posX = (source.getWidth() * x) + (Constants.BLOCK_SPACER * x);
            double posY = 0;

            entities.add(new Block(this, source,Constants.BLOCK_START.getX() + posX,Constants.BLOCK_START.getY() + posY));
        }
    }

    public void setPlayer(String player) {
        userName = player;
    }

    public GameController getController() {
        return controller;
    }

    public App getApp() {
        return app;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Entity getObject(Source source) {
        return entities.stream().filter(e -> e.getSource() == source).findFirst().orElse(null);
    }

    public Collection<Ghost> getGhosts() {
        return entities.stream().filter(e -> e instanceof Ghost).map(e -> (Ghost) e).toList();
    }

    public long getLevelTime() {
        return levelTime;
    }

    public InputListener getInputListener() {
        return inputListener;
    }
}
