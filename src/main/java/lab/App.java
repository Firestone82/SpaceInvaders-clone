package lab;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lab.enums.Constants;
import lab.enums.GameState;
import lab.enums.Source;
import lab.object.controller.MainController;

public class App extends Application {

	private MainController mainController;
	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		try {
			Font.loadFont(getClass().getResource("/assets/ARCADECLASSIC.TTF").toExternalForm(),100);
			Font.loadFont(getClass().getResource("/assets/ARCADE.TTF").toExternalForm(),100);

			mainController = new MainController(this, primaryStage);
			mainController.changeState(GameState.MENU);

			primaryStage.resizableProperty().set(false);
			primaryStage.getIcons().add(Source.ICON.getImage());
			primaryStage.setTitle(Constants.TITLE);
			primaryStage.setOnCloseRequest(this::exitProgram);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		mainController.getGame().stop();
	}
	
	private void exitProgram(WindowEvent evt) {
		System.exit(0);
	}

	public MainController getMainController() {
		return mainController;
	}

	public Stage getStage() {
		return primaryStage;
	}

	public String getResourcePath() {
		return "src/main/resources";
	}
}