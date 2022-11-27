package lab.object.controller.list;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lab.App;
import lab.enums.Constants;
import lab.enums.GameState;
import lab.interfaces.Controller;
import lab.object.GameScore;
import lab.object.other.Utils;

import java.util.List;

public class BoardController implements Controller {
    private App app;
    private Pane pane;

    @FXML TableView<GameScore> table;
    @FXML TableColumn<GameScore, String> tableUsername;
    @FXML TableColumn<GameScore, Integer> tableScore;
    @FXML TableColumn<GameScore, String> tableTime;

    private Scene scene;

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;

        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());

        tableUsername.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tableScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    public void draw() {
        List<GameScore> scoreList = app.getMainController().getScoreController().getScoreList().stream().sorted((o1, o2) -> o2.getScore() - o1.getScore()).limit(10).toList();

        table.setItems(FXCollections.observableArrayList(scoreList));
    }

    @FXML
    public void backToMenu() {
        app.getMainController().changeState(GameState.MENU);
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public Canvas getCanvas() {
        return null;
    }
}
