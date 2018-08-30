package checkers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CheckersController {
	// Constants
	public static int COLUMNS = 8;
	public static int ROWS = 8;
	public static double OFFSET = 45.0;
	public static double WIDTH = 44.0;
	public static double HEIGHT = 44.0;
	public static double RADIUS = 15.0;

	// View Fields
	@FXML
	private Pane _container;
	@FXML
	private Label _redLabel;
	@FXML
	private Label _blackLabel;
	@FXML
	private Label _winner;
	@FXML
	private Button _rules;
	@FXML
	private Label _turnLabel;

	// Fields
	// Represents each rectangle spot on board
	private Rectangle[][] _grid;
	// Holds checker pieces
	private Circle[][] _checkers;
	private int _rectangleX;
	private int _rectangleY;
	private int _circleX;
	private int _circleY;
	private boolean _circleClicked;
	private int _redScore;
	private int _blackScore;
	private String _prevColor;
	private String _currentColor;
	private boolean _newPlayer;
	private int _turnCounter;
	private boolean _pieceExists;

	// Methods
	
	// Create Checkers board
	public void initialize() {

		_grid = new Rectangle[COLUMNS][ROWS];
		_checkers = new Circle[COLUMNS][ROWS];
		_redScore = 0;
		_blackScore = 0;
		_circleClicked = false;
		_redLabel.setText(_redScore + " points");
		_blackLabel.setText(_blackScore + " points");
		_winner.setText("");
		_newPlayer = true;
		_prevColor = Color.RED.toString();
		_turnLabel.setText("Black goes first");
		_turnCounter = 1;
		_pieceExists = true;

		for (int x = 0; x < _grid.length; x++) {
			for (int y = 0; y < _grid[x].length; y++) {

				// Initialize checkers board
				Rectangle r = new Rectangle();
				r.setX(OFFSET * x);
				r.setY(OFFSET * y);
				r.setWidth(WIDTH);
				r.setHeight(HEIGHT);
				r.setOnMouseClicked(this::handleRectangle);
				if (x % 2 == 0) {
					if (y % 2 == 0) {
						r.setFill(Color.LIGHTBLUE);
					} else {
						r.setFill(Color.WHITE);
					}
				} else {
					if (y % 2 == 1) {
						r.setFill(Color.LIGHTBLUE);
					} else {
						r.setFill(Color.WHITE);
					}
				}
				_container.getChildren().add(r);
				_grid[x][y] = r;

				// Circles
				Circle c = new Circle();
				// Placing pieces in correct spots on board
				if (y != 3 && y != 4) {
					if (y == 0 || y == 1 || y == 2) {
						if (x % 2 == 0 && y % 2 == 0) {
							c.setCenterX(OFFSET * x + (HEIGHT / 2));
							c.setCenterY(OFFSET * y + (HEIGHT / 2));
							c.setRadius(RADIUS);
							c.setFill(Color.RED);
							c.setOnMouseClicked(this::handleCircle);
						} else if (x % 2 == 1 && y % 2 == 1) {
							c.setCenterX(OFFSET * x + (HEIGHT / 2));
							c.setCenterY(OFFSET * y + (HEIGHT / 2));
							c.setRadius(RADIUS);
							c.setFill(Color.RED);
							c.setOnMouseClicked(this::handleCircle);
						}
					}

					if (y >= 5) {
						if (x % 2 == 0 && y % 2 == 0) {
							c.setCenterX(OFFSET * x + (HEIGHT / 2));
							c.setCenterY(OFFSET * y + (HEIGHT / 2));
							c.setRadius(RADIUS);
							c.setFill(Color.BLACK);
							c.setOnMouseClicked(this::handleCircle);
						} else if (x % 2 == 1 && y % 2 == 1) {
							c.setCenterX(OFFSET * x + (HEIGHT / 2));
							c.setCenterY(OFFSET * y + (HEIGHT / 2));
							c.setRadius(RADIUS);
							c.setFill(Color.BLACK);
							c.setOnMouseClicked(this::handleCircle);
						}
					}
				}
				_container.getChildren().add(c);
				_checkers[x][y] = c;
			}
		}
	}

	// When a rectangle is clicked:
	private void handleRectangle(MouseEvent event) {
		Rectangle target = (Rectangle) event.getTarget();
		int column = (int) (target.getX() / OFFSET);
		int row = (int) (target.getY() / OFFSET);
		_rectangleX = column;
		_rectangleY = row;

		// Checking to see if the circle & rectangle are both valid for the turn
		if (target.getFill() == Color.WHITE) {
			_circleClicked = false;
		}
		if ((_rectangleX - _circleX) > 3 || (_circleY - _rectangleY) > 3 || (_circleX - _rectangleX) > 3
				|| (_rectangleY - _circleY) > 3) {
			_circleClicked = false;
		}

		// Deciding turns
		if (_prevColor.equals(_currentColor)) {
			_newPlayer = false;
		} else if ((_prevColor.equalsIgnoreCase(Color.RED.toString()) && _currentColor.equals(Color.MAGENTA.toString()))
				|| (_prevColor.equalsIgnoreCase(Color.MAGENTA.toString()) && _currentColor.equals(Color.RED.toString()))
				|| (_prevColor.equalsIgnoreCase(Color.BLACK.toString()) && _currentColor.equals(Color.BLUE.toString()))
				|| (_prevColor.equalsIgnoreCase(Color.BLUE.toString())
						&& _currentColor.equals(Color.BLACK.toString()))) {
			_newPlayer = false;
		} else {
			_newPlayer = true;
			if (_turnCounter % 2 == 1 && _circleClicked) {
				_turnLabel.setText("Red's turn");
				_turnLabel.setTextFill(Color.RED);
			} else if (_circleClicked) {
				_turnLabel.setText("Black's turn");
				_turnLabel.setTextFill(Color.BLACK);
			}
		}
		////system.out.println("current: " + _currentColor);
		//system.out.println("prev: " + _prevColor);

		// While there was a previous circle clicked and it's a new player's
		// turn:
		while (_circleClicked && _newPlayer) {
			_prevColor = _currentColor;
			// The new circle is
			Circle circle = new Circle();
			circle.setRadius(RADIUS);
			circle.setCenterX((OFFSET * (_rectangleX) + (HEIGHT / 2)));
			circle.setCenterY((OFFSET * (_rectangleY) + (HEIGHT / 2)));
			circle.setFill(_checkers[_circleX][_circleY].getFill());
			circle.setOnMouseClicked(this::handleCircle);
			_checkers[_rectangleX][_rectangleY] = circle;
			if (_rectangleY == 7 && circle.getFill() == Color.RED) {
				circle.setFill(Color.MAGENTA);
			}
			if (_rectangleY == 0 && circle.getFill() == Color.BLACK) {
				circle.setFill(Color.BLUE);
			}
			// IT'S JUMPING TIME
			// If the piece you're jumping over exists and isn't the same color
			// as the previous piece:
			if (_checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY - ((_circleY - _rectangleY) / 2)] != null
					&& _checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY - ((_circleY - _rectangleY) / 2)]
							.getFill() != circle.getFill()) {
				if (_checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY
						- ((_circleY - _rectangleY) / 2)] == null) {
					_pieceExists = false;
				}
				// Removing the piece that was jumped over
				_container.getChildren().remove(_checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY
						- ((_circleY - _rectangleY) / 2)]);
				// Adding points
				if ((_checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY - ((_circleY - _rectangleY) / 2)]
						.getFill() == Color.BLACK
						|| _checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY
								- ((_circleY - _rectangleY) / 2)].getFill() == Color.BLUE)
						&& _pieceExists) {

					_redScore++;
					_redLabel.setText(_redScore + " points");
					_newPlayer = true;

				} else if ((_checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY
						- ((_circleY - _rectangleY) / 2)].getFill() == Color.RED
						|| _checkers[_circleX - ((_circleX - _rectangleX) / 2)][_circleY
								- ((_circleY - _rectangleY) / 2)].getFill() == Color.MAGENTA)
						&& _pieceExists) {

					_blackScore++;
					_blackLabel.setText(_blackScore + " points");
					_newPlayer = true;

				}

			}
			// Changing the text when there's a winner
			if (_redScore == 12) {
				_turnLabel.setTextFill(Color.TRANSPARENT);
				_winner.setText("RED WINS!!");
				_winner.setTextFill(Color.RED);
			} else if (_blackScore == 12) {
				_turnLabel.setTextFill(Color.TRANSPARENT);
				_winner.setText("BLACK WINS!!");
			}

			// Moving the piece during a normal turn. (adds the new circle where
			// you've selected and removes the old circle, thus "moving" it)
			_container.getChildren().add(circle);
			_container.getChildren().remove(_checkers[_circleX][_circleY]);
			// Changing turns
			if (_circleClicked) {
				_turnCounter++;
			}

			// RIP circle
			_circleClicked = false;

		}

	}

	// When a circle is clicked:
	private void handleCircle(MouseEvent event) {
		Circle targetCircle = (Circle) event.getTarget();
		int column = (int) ((targetCircle.getCenterX() - (WIDTH / 2)) / OFFSET);
		int row = (int) ((targetCircle.getCenterY() - (WIDTH / 2)) / OFFSET);
		_circleX = column;
		_circleY = row;
		// _circleClicked marks that a circle has been selected so that
		// handleRectangle() knows it's able to move
		_circleClicked = true;
		_currentColor = targetCircle.getFill().toString();
	
	}

	// the new and improved rules button
	public void changeText() {
		_rules.setText("No.");
	}

}
