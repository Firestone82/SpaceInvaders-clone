package lab.object.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lab.App;
import lab.enums.GameState;
import lab.enums.Source;
import lab.interfaces.Controller;
import lab.object.Game;
import lab.object.GameScore;
import lab.object.controller.list.*;
import lab.object.entity.list.Score;

import java.util.Timer;
import java.util.TimerTask;

public class MainController {
    private final App app;
    private final Stage primaryStage;

    private final Game game;
    private GameState state = GameState.MENU;
    private MenuController menuController;
    private GameController gameController;
    private BoardController boardController;
    private ScoreController scoreController;
    private DatabaseController databaseController;

    public MainController(App app, Stage primaryStage) {
        this.app = app;
        this.primaryStage = primaryStage;

        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource(GameState.MENU.getFXML()));
            Pane menuPane = menuLoader.load();
            menuController = menuLoader.getController();
            menuController.init(app, menuPane);

            FXMLLoader gameLoader = new FXMLLoader(App.class.getResource(GameState.PLAYING.getFXML()));
            Pane gamePane = gameLoader.load();
            gameController = gameLoader.getController();
            gameController.init(app, gamePane);

            FXMLLoader boardLoader = new FXMLLoader(App.class.getResource(GameState.LEADERBOARD.getFXML()));
            Pane boardPane = boardLoader.load();
            boardController = boardLoader.getController();
            boardController.init(app, boardPane);

            databaseController = new DatabaseController(app);

            scoreController = new ScoreController(app,this);
            scoreController.loadScore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.game = new Game(app, gameController);
    }

    public void changeState(GameState state) {
        GameState prevState = this.state;
        this.state = state;

        try {
            switch (state) {
                case MENU -> {
                    if (prevState == GameState.OVER) {
                        game.stop();
                    }

                    primaryStage.setScene(menuController.getScene());
                }

                case PLAYING -> {
                    primaryStage.setScene(gameController.getScene());
                    game.start();
                }

                case PAUSED -> {
                    game.pause();
                }

                case OVER -> {
                    game.stop();
                    game.save();
                }

                case LEADERBOARD -> {
                    primaryStage.setScene(boardController.getScene());
                    boardController.draw();
                }
            }

            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameState getState() {
        return state;
    }

    public Controller getController() {
        switch (state) {
            case MENU -> {
                return menuController;
            }

            case PLAYING, PAUSED, OVER -> {
                return gameController;
            }

            case LEADERBOARD -> {
                return boardController;
            }
        }

        return null;
    }

    public void exit() {
        menuController.exitGame();
    }

    public Game getGame() {
        return game;
    }

    public ScoreController getScoreController() {
        return scoreController;
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }
}
