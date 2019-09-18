package tictactoe;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class GameBoard extends GridPane {
	private GameTile[][] board;
	private HashMap<Integer, GameTile> map;
	
	private GameAI ai;
	
	private Image p1Image;
	private Image p2Image;
	private Image aiImage;
	
	Line winningLine;
	
	private boolean player1Turn;
	private boolean gameOver;
	
	private int aiDifficulty; // (0) no ai, (1-3) easy, medium, hard
	
	public GameBoard(int aiDifficulty) {
		board = new GameTile[3][3];
		map = new HashMap<>();
		
		winningLine = new Line();
		
		p1Image = new Image("tictactoe_X.png", 200, 200, false, true);
		p2Image = new Image("tictactoe_O.png", 200, 200, false, true);
		aiImage = new Image("tictactoe_O.png", 200, 200, false, true);
		
		player1Turn = true;
		gameOver = false;
		this.aiDifficulty = aiDifficulty;
		
		makeNewBoard();
		
		ai = new GameAI(this, aiImage);
	}
	
	public void reset() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col].reset();
			}
		}
		player1Turn = true;
		gameOver = false;
	}
	
	private void makeNewBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = new GameTile();
				add(board[row][col], col, row);
			}
		}
		map.put(1, board[0][0]);
		map.put(2, board[0][1]);
		map.put(3, board[0][2]);
		map.put(4, board[1][0]);
		map.put(5, board[1][1]);
		map.put(6, board[1][2]);
		map.put(7, board[2][0]);
		map.put(8, board[2][1]);
		map.put(9, board[2][2]);
	}
	
	public GameTile[][] getBoard() {
		return board;
	}
	
	public GameTile getTileAt(int i) {
		return map.getOrDefault(i, null);
	}
	
	public void drawOnTile(int i) {
		if (gameOver) {
			return;
		}
		
		GameTile temp = map.get(i);
		
		if (temp.getValue() == 0) {
			if (player1Turn) {
				temp.draw(p1Image);
				temp.setValue(1);
			} else {
				temp.draw(p2Image);
				temp.setValue(2);
			}
			player1Turn = !player1Turn;
		}
	}
	
	public void aiMove() {
		if (!gameOver && !player1Turn) {
			if (aiDifficulty == 1) {
				ai.easyMove();
			} else if (aiDifficulty == 2) {
				ai.medMove();
			} else if (aiDifficulty == 3) {
				ai.hardMove();
			}
			player1Turn = true;
		}
	}
	
	public int checkGame() {
		int row = checkRows();
		int col = checkCols();
		int dia = checkDiags();
		
		if (row != -1) {
			return row;
		} else if (col != -1) {
			return col;
		} else if (dia != -1) {
			return dia;
		} else if (checkTie()) {
			return 4;
		}
		return -1;
	}
	
	private boolean checkTie() {
		for (int i = 1; i <= 9; i++) {
			if (map.get(i).getValue() == 0) {
				return false;
			}
		}
		gameOver = true;
		return true;
	}
	
	private int checkRows() {
		if (map.get(1).getValue() != 0 && map.get(1).getValue() == map.get(2).getValue()
				&& map.get(1).getValue() == map.get(3).getValue()) {
			gameOver = true;
			setWinningLine(1, 2, 3);
			return map.get(1).getValue();
			
		} else if (map.get(4).getValue() != 0 && map.get(4).getValue() == map.get(5).getValue()
				&& map.get(4).getValue() == map.get(6).getValue()) {
			gameOver = true;
			setWinningLine(4, 5, 6);
			return map.get(4).getValue();
			
		} else if (map.get(7).getValue() != 0 && map.get(7).getValue() == map.get(8).getValue()
				&& map.get(7).getValue() == map.get(9).getValue()) {
			gameOver = true;
			setWinningLine(7, 8, 9);
			return map.get(7).getValue();
		}
		return -1;
	}
	
	private int checkCols() {
		if (map.get(1).getValue() != 0 && map.get(1).getValue() == map.get(4).getValue()
				&& map.get(1).getValue() == map.get(7).getValue()) {
			gameOver = true;
			setWinningLine(1, 4, 7);
			return map.get(1).getValue();
			
		} else if (map.get(2).getValue() != 0 && map.get(2).getValue() == map.get(5).getValue()
				&& map.get(2).getValue() == map.get(8).getValue()) {
			gameOver = true;
			setWinningLine(2, 5, 8);
			return map.get(2).getValue();
			
		} else if (map.get(3).getValue() != 0 && map.get(3).getValue() == map.get(6).getValue()
				&& map.get(3).getValue() == map.get(9).getValue()) {
			gameOver = true;
			setWinningLine(3, 6, 9);
			return map.get(3).getValue();
		}
		return -1;
	}
	
	private int checkDiags() {
		if (map.get(1).getValue() != 0 && map.get(1).getValue() == map.get(5).getValue()
				&& map.get(1).getValue() == map.get(9).getValue()) {
			gameOver = true;
			setWinningLine(1, 5, 9);
			return map.get(1).getValue();
			
		} else if (map.get(3).getValue() != 0 && map.get(3).getValue() == map.get(5).getValue()
				&& map.get(3).getValue() == map.get(7).getValue()) {
			gameOver = true;
			setWinningLine(3, 5, 7);
			return map.get(3).getValue();
		}
		return -1;
	}
	
	private void setWinningLine(int loc1, int loc2, int loc3) {
		winningLine.setStartX(map.get(loc1).getCenterX());
		winningLine.setStartY(map.get(loc1).getCenterY());
		
		winningLine.setEndX(map.get(loc3).getCenterX());
		winningLine.setEndY(map.get(loc3).getCenterY());
	}
	
	public Line getWinningLine() {
		return winningLine;
	}
	
	public Image getP1Image() {
		return p1Image;
	}
	
	public Image getP2Image() {
		return p2Image;
	}
	
	public Image getAIImage() {
		return aiImage;
	}
	
	public int getAIDifficulty() {
		return aiDifficulty;
	}
	
	public void setP1Image(Image image) {
		p1Image = image;
	}
	
	public void setP2Image(Image image) {
		p2Image = image;
	}
	
	public void setAIImage(Image image) {
		aiImage = image;
		ai.setImage(image);
	}
	
	public void setAIDifficulty(int dif) {
		aiDifficulty = dif;
	}
	
	public void setLine(Line line) {
		winningLine = line;
	}

}
