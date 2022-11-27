package lab.object.controller.list;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lab.App;
import lab.enums.GameState;
import lab.enums.Source;
import lab.interfaces.Controller;

public class MenuController implements Controller {
    private App app;
    private Pane pane;

    @FXML Canvas logoCanvas;
    @FXML TextField userNameInput;
    private Scene scene;

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;

        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());
        this.scene.addEventHandler(KeyEvent.ANY, (event) -> app.getMainController().getGame().getInputListener().fire(event));

        logoCanvas.getGraphicsContext2D().drawImage(Source.LOGO.getImage(),50,70);
    }

    @FXML
    public void startGame() {
        if (userNameInput.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("You must enter your username!");
            alert.showAndWait();
        } else {
            app.getMainController().changeState(GameState.PLAYING);
            app.getMainController().getGame().setPlayer(userNameInput.getText());
        }
    }

    @FXML
    public void exitGame() {
        app.getStage().close();
    }

    @FXML
    public void showLeaderboard() {
        app.getMainController().changeState(GameState.LEADERBOARD);
    }

    public Scene getScene() {
        return scene;
    }

    public Canvas getCanvas() {
        return logoCanvas;
    }
}
