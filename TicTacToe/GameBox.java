package tictactoe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class GameBox extends HBox {
	private VBox vbox;
	private VBox topVBox;
	private VBox midVBox;
	private VBox botVBox;
	private Pane pane;
	private GameBoard gameBoard;
	
	private Line winningLine;
	
	private Integer player1Wins;
	private Integer player2Wins;
	private Integer aiWins;
	
	private ImageView p1View;
	private ImageView p2View;
	private ImageView p3View;
	
	private Label p1Wins;
	private Label p2Wins;
	private Label p3Wins;
	
	private boolean gameOver;
	
	public GameBox(int aiDifficulty) {
		vbox = new VBox(50);
		topVBox = new VBox(5);
		midVBox = new VBox(20);
		botVBox = new VBox(20);
		pane = new Pane();
		
//		stackPane.setMinSize(600, 600);
		
		vbox.setPadding(new Insets(0, 0, 0, 50));
		
		gameBoard = new GameBoard(aiDifficulty);
		
		player1Wins = 0;
		player2Wins = 0;
		aiWins = 0;
		gameOver = false;
		
		Label p1 = new Label("Player 1: ");
		Label p2 = new Label("Player 2: ");
		Label p3 = new Label("A.I.:        ");
		p1Wins = new Label("Player 1 Wins: " + player1Wins.toString());
		p2Wins = new Label("Player 2 Wins: " + player2Wins.toString());
		p3Wins = new Label("A.I. Wins:        " + aiWins.toString());
		
		p1Wins.setFont(Font.font(15));
		p2Wins.setFont(Font.font(15));
		p3Wins.setFont(Font.font(15));
		
		HBox p1Pic = new HBox();
		HBox p2Pic = new HBox();
		HBox p3Pic = new HBox();
		
		p1Pic.setAlignment(Pos.CENTER);
		p2Pic.setAlignment(Pos.CENTER);
		p3Pic.setAlignment(Pos.CENTER);
		
		p1View = new ImageView();
		p2View = new ImageView();
		p3View = new ImageView();
		
		p1View.setPreserveRatio(true);
		p2View.setPreserveRatio(true);
		p3View.setPreserveRatio(true);
		
		p1View.setFitWidth(50);
		p2View.setFitWidth(50);
		p3View.setFitWidth(50);
		
		p1View.setFitHeight(50);
		p2View.setFitHeight(50);
		p3View.setFitHeight(50);
		
		p1View.setImage(gameBoard.getP1Image());
		p2View.setImage(gameBoard.getP2Image());
		p3View.setImage(gameBoard.getAIImage());
		
		p1Pic.getChildren().addAll(p1, p1View);
		p2Pic.getChildren().addAll(p2, p2View);
		p3Pic.getChildren().addAll(p3, p3View);
		
		winningLine = new Line();
		winningLine.setStroke(Color.WHITE);
		winningLine.setStrokeWidth(6);
		
		gameBoard.setLine(winningLine);
		
		vbox.setAlignment(Pos.CENTER);
		topVBox.setAlignment(Pos.CENTER);
		midVBox.setAlignment(Pos.CENTER);
		botVBox.setAlignment(Pos.CENTER);

		topVBox.getChildren().addAll(p1Pic, p2Pic, p3Pic);
		midVBox.getChildren().addAll(p1Wins, p2Wins, p3Wins);
		pane.getChildren().addAll(gameBoard);
		vbox.getChildren().addAll(topVBox, midVBox, botVBox);
		
		getChildren().addAll(pane, vbox);
		
		gameBoard.setOnMouseClicked(e -> {
			double xVal = e.getX();
			double yVal = e.getY();
			
			if (xVal <= 200) {
				if (yVal <= 200) { 				// 1
					gameBoard.drawOnTile(1);
				} else if (yVal <= 400) { 		// 4
					gameBoard.drawOnTile(4);
				} else if (yVal <= 600) { 		// 7
					gameBoard.drawOnTile(7);
				}
			} else if (xVal <= 400) {
				if (yVal <= 200) { 				// 2
					gameBoard.drawOnTile(2);
				} else if (yVal <= 400) {		// 5
					gameBoard.drawOnTile(5);
				} else if (yVal <= 600) { 		// 8
					gameBoard.drawOnTile(8);
				}
			} else if (xVal <= 600) {
				if (yVal <= 200) { 				// 3
					gameBoard.drawOnTile(3);
				} else if (yVal <= 400) { 		// 6
					gameBoard.drawOnTile(6);
				} else if (yVal <= 600) { 		// 9
					gameBoard.drawOnTile(9);
				}
			}
			check();
			
			if (gameBoard.getAIDifficulty() != 0) {
				gameBoard.aiMove();
			}
			check();
		});
	}
		
	public void check() {
		int check = gameBoard.checkGame();
		
		if (check != -1 && !gameOver) {
			setGameOver();
			
			if (check != 4) {
				drawWinningLine();
				
				if (check == 1) {
					incP1Win();
				} else if (check == 2) {
					incP2Win();
				} else if (check == 3) {
					incAIWin();
				}
			}
		}
	}
	
	public void drawWinningLine() {
		Line innerLine = new Line();
		innerLine.setStrokeWidth(3);
		
		double startX = winningLine.getStartX();
		double startY = winningLine.getStartY();
		double endX = winningLine.getEndX();
		double endY = winningLine.getEndY();
		
		if (startX == endX && startY != endY) { // Vertical line
			innerLine.setStartX(winningLine.getStartX());
			innerLine.setStartY(winningLine.getStartY() + 2);
			innerLine.setEndX(winningLine.getEndX());
			innerLine.setEndY(winningLine.getEndY() - 2);
		} else if (startX != endX && startY == endY) { // Horizontal line
			innerLine.setStartX(winningLine.getStartX() + 2);
			innerLine.setStartY(winningLine.getStartY());
			innerLine.setEndX(winningLine.getEndX() - 2);
			innerLine.setEndY(winningLine.getEndY());
		} else if (startX < 300) {
			innerLine.setStartX(winningLine.getStartX() + 2);
			innerLine.setStartY(winningLine.getStartY() + 2);
			innerLine.setEndX(winningLine.getEndX() - 2);
			innerLine.setEndY(winningLine.getEndY() - 2);
		} else {
			innerLine.setStartX(winningLine.getStartX() - 2);
			innerLine.setStartY(winningLine.getStartY() + 2);
			innerLine.setEndX(winningLine.getEndX() + 2);
			innerLine.setEndY(winningLine.getEndY() - 2);
		}
		
		pane.getChildren().addAll(winningLine, innerLine);
	}
	
	public Image getP1Image() {
		return gameBoard.getP1Image();
	}
	
	public Image getP2Image() {
		return gameBoard.getP2Image();
	}
	
	public Image getAIImage() {
		return gameBoard.getAIImage();
	}
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}
	
	public VBox getVBox() {
		return vbox;
	}
	
	public VBox getTopVBox() {
		return topVBox;
	}
	
	public VBox getBotVBox() {
		return botVBox;
	}
	
	public boolean gameOver() {
		return gameOver;
	}
	
	public void resetBoard() {
		gameOver = false;
		gameBoard.reset();
		if (pane.getChildren().size() > 1) {
			pane.getChildren().remove(1);
			pane.getChildren().remove(1);
		}
	}
	
	public void reset() {
		resetBoard();
		player1Wins = 0;
		player2Wins = 0;
		aiWins = 0;
		
		p1Wins.setText("Player 1 Wins: " + player1Wins.toString());
		p2Wins.setText("Player 2 Wins: " + player2Wins.toString());
		p3Wins.setText("A.I. Wins:        " + aiWins.toString());
	}
	
	public void setP1Image(Image image) {
		gameBoard.setP1Image(image);
		p1View.setImage(image);
	}
	
	public void setP2Image(Image image) {
		gameBoard.setP2Image(image);
		p2View.setImage(image);
	}
	
	public void setAIImage(Image image) {
		gameBoard.setAIImage(image);
		p3View.setImage(image);
	}
	
	public void setAIDifficulty(int diff) {
		gameBoard.setAIDifficulty(diff);
	}
	
	public void setGameOver() {
		gameOver = !gameOver;
	}
	
	public void incP1Win() {
		player1Wins++;
		p1Wins.setText("Player 1 Wins: " + player1Wins.toString());
	}
	
	public void incP2Win() {
		player2Wins++;
		p2Wins.setText("Player 2 Wins: " + player2Wins.toString());
	}
	
	public void incAIWin() {
		aiWins++;
		p3Wins.setText("A.I. Wins:        " + aiWins.toString());
	}

}
