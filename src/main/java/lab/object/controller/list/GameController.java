package lab.object.controller.list;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lab.App;
import lab.interfaces.Controller;

public class GameController implements Controller {
    private App app;
    private Pane pane;

    @FXML Canvas gameCanvas;
    private Scene scene;

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;

        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());
        this.scene.addEventHandler(KeyEvent.ANY, (event) -> app.getMainController().getGame().getInputListener().fire(event));
    }

    public Scene getScene() {
        return scene;
    }

    public Canvas getCanvas() {
        return gameCanvas;
    }
}
