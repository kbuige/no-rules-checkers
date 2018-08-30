package checkers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RulesWindow {

	private Stage _stage;

	// hahaha we tried to make a rules window but we couldn't get it to
	// initialize at 5am so we just decided it was no rules checkers. ignore
	// this (but we worked on it for a bit so might as well leave it in case it
	// comes to fruition in the future ya feel)

	public RulesWindow() {
		try {
			CheckersController controller = new CheckersController();
			String view = "RulesView.fxml";
			String title = "Rules";

			// Construct the FXML Loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
			loader.setController(controller);

			// Load the Scene
			Scene scene = new Scene(loader.load());

			// Setup the window
			_stage = new Stage();
			_stage.setScene(scene);
			_stage.setTitle(title);
			_stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show() {
		_stage.show();
		_stage.toFront();
	}
}
