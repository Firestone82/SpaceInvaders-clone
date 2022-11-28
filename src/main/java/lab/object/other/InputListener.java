package lab.object.other;

import javafx.scene.input.KeyEvent;
import lab.App;
import lab.enums.Direction;
import lab.enums.GameState;
import lab.enums.Source;
import lab.object.Game;
import lab.object.controller.list.MenuController;
import lab.object.controller.list.ScoreController;
import lab.object.entity.list.*;

public class InputListener {
    private final App app;
    private final Game game;

    private Direction direction = Direction.NONE;
    private boolean shoot = false;
    private boolean press = false;

    public InputListener(App app, Game game) {
        this.app = app;
        this.game = game;
    }

    public void fire(KeyEvent event) {
        switch (event.getCode()) {
            case NUMPAD3, DIGIT3 -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    game.speedUp();

                    System.out.println("Ghosts speeded up!");

                    press = false;
                }
            }

            case NUMPAD2, DIGIT2 ->  {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    ScoreController scoreController = app.getMainController().getScoreController();
                    scoreController.clearScore();

                    System.out.println("Score Database Was Cleared!");

                    press = false;
                }
            }

            case NUMPAD1, DIGIT1 -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    UFO ufo = ((UFO) game.getObject(Source.UFO));

                    if (ufo.isDisabled()) {
                        ufo.execute();

                        System.out.println("UFO Forced to go!");
                    }

                    press = false;
                }
            }

            case ENTER -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    if (app.getMainController().getState() == GameState.MENU) {
                        ((MenuController) app.getMainController().getController()).startGame();
                    }

                    press = false;
                }
            }

            case ESCAPE -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    press = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
                    switch (app.getMainController().getState()) {
                        case MENU, OVER -> app.getMainController().exit();
                        case LEADERBOARD -> app.getMainController().changeState(GameState.MENU);
                        case PLAYING -> app.getMainController().changeState(GameState.PAUSED);
                        case PAUSED -> app.getMainController().changeState(GameState.PLAYING);
                    }

                    press = false;
                }
            }

            case W, UP, SPACE -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    shoot = true;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && shoot) {
                    ShipBullet bullet = ((ShipBullet) game.getObject(Source.SHIP_BULLET));

                    if (bullet.isDisabled()) {
                        bullet.execute();
                    }

                    shoot = false;
                }
            }

            case A, LEFT -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    direction = Direction.LEFT;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && direction == Direction.LEFT) {
                    direction = Direction.NONE;
                }
            }

            case D, RIGHT -> {
                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                    direction = Direction.RIGHT;
                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && direction == Direction.RIGHT) {
                    direction = Direction.NONE;
                }
            }

//            default -> {
//                if (event.getEventType() == KeyEvent.KEY_PRESSED) {
//                    press = true;
//                } else if (event.getEventType() == KeyEvent.KEY_RELEASED && press) {
//                    if (app.getMainController().getState() == GameState.OVER) {
//                        app.getMainController().changeState(GameState.MENU);
//
//                        ((Score) game.getObject(Source.SCORE)).resetPoints();
//                        ((Lives) game.getObject(Source.LIVES)).removeLive();
//                        game.addBlocks();
//                        game.addGhosts();
//                    }
//
//                    press = false;
//                }
//            }
        }
    }

    public void tick(double deltaT) {
        ((Ship) game.getObject(Source.SHIP)).move(direction, deltaT);
    }
}
