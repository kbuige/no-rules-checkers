package checkers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckersWindow {

	public CheckersWindow() {
		try {
			// Setup a Controller and some variables that plug-in to the
			// boilerplate below
			CheckersController controller = new CheckersController();
			String viewFile = "CheckersView.fxml";
			String windowTitle = "NO RULES CHECKERS";
			// Setup the View's FXML Loader
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource(viewFile));
			loader.setController(controller);
			// Show the window
			Stage stage = new Stage();
			stage.setScene(new Scene(loader.load()));
			stage.setTitle(windowTitle);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
