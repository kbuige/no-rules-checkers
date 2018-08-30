package checkers;

import javafx.application.Application;

import javafx.stage.Stage;

public class CheckersApp extends Application {

	public static void main(String[] args) {
		Application.launch();
	}

	public void start(Stage s) {
		new CheckersWindow();
	}

}
