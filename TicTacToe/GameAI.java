package tictactoe;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class GameAI {
	private GameBoard gameBoard;
	private Image aiImage;
	private Random rand;
	
	private MutableInt val1;
	private MutableInt val2;
	private MutableInt val3;
	private MutableInt val4;
	private MutableInt val5;
	private MutableInt val6;
	private MutableInt val7;
	private MutableInt val8;
	private MutableInt val9;
	
	public GameAI(GameBoard board, Image image) {
		gameBoard = board;
		aiImage = image;
		rand = new Random();
		
		val1 = gameBoard.getTileAt(1).getMutableInt();
		val2 = gameBoard.getTileAt(2).getMutableInt();
		val3 = gameBoard.getTileAt(3).getMutableInt();
		val4 = gameBoard.getTileAt(4).getMutableInt();
		val5 = gameBoard.getTileAt(5).getMutableInt();
		val6 = gameBoard.getTileAt(6).getMutableInt();
		val7 = gameBoard.getTileAt(7).getMutableInt();
		val8 = gameBoard.getTileAt(8).getMutableInt();
		val9 = gameBoard.getTileAt(9).getMutableInt();
	}
	
	// Tries to avoid getting in the way of player
	public void easyMove() {
		int spot = findEasySpot();
		
		if (spot != -1) {
			gameBoard.getTileAt(spot).draw(aiImage);
			gameBoard.getTileAt(spot).setValue(3);
		} else {
			medMove();
		}
		//printVals();
	}
	
	// Picks random open spot
	public void medMove() {
		int randSpot = findRandomSpot();
		
		if (randSpot != -1) {
			gameBoard.getTileAt(randSpot).draw(aiImage);
			gameBoard.getTileAt(randSpot).setValue(3);
		}
		//printVals();
	}
	
	// Tries to always gets in way of player
	public void hardMove() {
		int spot = findHardSpot();
		
		if (spot != -1) {
			gameBoard.getTileAt(spot).draw(aiImage);
			gameBoard.getTileAt(spot).setValue(3);
		} else {
			medMove();
		}
		//printVals();
	}
	
	public void setImage(Image image) {
		aiImage = image;
	}
	
	private int findHardSpot() {
		int[] arr = new int[4];
		
		if (oneInner()) {
			arr[0] = 1;
			arr[1] = 3;
			arr[2] = 7;
			arr[3] = 9;
			return arr[rand.nextInt(4)];
		} else if (oneOuter()) {
			return 5;
		} else if (findWinningSpot() != -1) {
			return findWinningSpot();
		} else if (findBlockSpot() != -1) {
			return findBlockSpot();
		} else if (findOkSpot() != -1) {
			return findOkSpot();
		}
		return -1;
	}
	
	// Board only has 1 piece in middle
	private boolean oneInner() {
		return (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0
				&& val4.getVal() == 0 && val5.getVal() == 1 && val6.getVal() == 0
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0);
	}
	
	// Board only has 1 piece and is not in middle
	private boolean oneOuter() {
		return ((val1.getVal() == 1 && val2.getVal() == 0 && val3.getVal() == 0
				&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0)
				
			|| (val1.getVal() == 0 && val2.getVal() == 1 && val3.getVal() == 0
				&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0)
				
			|| (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 1
				&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0)
				
			|| (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0
				&& val4.getVal() == 1 && val5.getVal() == 0 && val6.getVal() == 0
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0)
				
			|| (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0
				&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 1
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0)
			
			|| (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0
				&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0
				&& val7.getVal() == 1 && val8.getVal() == 0 && val9.getVal() == 0)
			
			|| (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0
			&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0
			&& val7.getVal() == 0 && val8.getVal() == 1 && val9.getVal() == 0)
			
			|| (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0
				&& val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0
				&& val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 1));
	}
	
	// Find a row with 2 player pieces to block
	private int findBlockSpot() {
		int row = findTwoInRow(1);
		int col = findTwoInCol(1);
		int diag = findTwoInDiag(1);
		
		if (row != -1) {
			return row;
		} else if (col != -1) {
			return col;
		} else if (diag != -1) {
			return diag;
		}
		return -1;
	}
	
	// Find a row with 2 ai pieces already
	private int findWinningSpot() {
		int row = findTwoInRow(3);
		int col = findTwoInCol(3);
		int diag = findTwoInDiag(3);
		
		if (row != -1) {
			return row;
		} else if (col != -1) {
			return col;
		} else if (diag != -1) {
			return diag;
		}
		return -1;
	}
	
	// Find a row with 1 ai piece already and other spots empty
	private int findOkSpot() {
		int row = findOneInRow(3);
		int col = findOneInCol(3);
		int diag = findOneInDiag(3);
		
		if (row != -1) {
			return row;
		} else if (col != -1) {
			return col;
		} else if (diag != -1) {
			return diag;
		}
		return -1;
	}
	
	private int findOneInRow(int findVal) {
		int[] arr = new int[2];
		
		// First row
		if (val1.getVal() == findVal && val2.getVal() == 0 && val3.getVal() == 0) {
			arr[0] = 2;
			arr[1] = 3;
			return arr[rand.nextInt(2)];
		} else if (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == findVal) {
			arr[0] = 1;
			arr[1] = 2;
			return arr[rand.nextInt(2)];
		} else if (val1.getVal() == 0 && val2.getVal() == findVal && val3.getVal() == 0) {
			arr[0] = 1;
			arr[1] = 3;
			return arr[rand.nextInt(2)];
		}
		
		// Second row
		if (val4.getVal() == findVal && val5.getVal() == 0 && val6.getVal() == 0) {
			arr[0] = 5;
			arr[1] = 6;
			return arr[rand.nextInt(2)];
		} else if (val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == findVal) {
			arr[0] = 4;
			arr[1] = 5;
			return arr[rand.nextInt(2)];
		} else if (val4.getVal() == 0 && val5.getVal() == findVal && val6.getVal() == 0) {
			arr[0] = 4;
			arr[1] = 6;
			return arr[rand.nextInt(2)];
		}
		
		// Third row
		if (val7.getVal() == findVal && val8.getVal() == 0 && val9.getVal() == 0) {
			arr[0] = 8;
			arr[1] = 9;
			return arr[rand.nextInt(2)];
		} else if (val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == findVal) {
			arr[0] = 7;
			arr[1] = 8;
			return arr[rand.nextInt(2)];
		} else if (val7.getVal() == 0 && val8.getVal() == findVal && val9.getVal() == 0) {
			arr[0] = 7;
			arr[1] = 9;
			return arr[rand.nextInt(2)];
		}
		return -1;
	}
	
	private int findOneInCol(int findVal) {
		int[] arr = new int[2];
		
		// First col
		if (val1.getVal() == findVal && val4.getVal() == 0 && val7.getVal() == 0) {
			arr[0] = 4;
			arr[1] = 7;
			return arr[rand.nextInt(2)];
		} else if (val1.getVal() == 0 && val4.getVal() == 0 && val7.getVal() == findVal) {
			arr[0] = 1;
			arr[1] = 4;
			return arr[rand.nextInt(2)];
		} else if (val1.getVal() == 0 && val4.getVal() == findVal && val7.getVal() == 0) {
			arr[0] = 1;
			arr[1] = 7;
			return arr[rand.nextInt(2)];
		}
		
		// Second col
		if (val2.getVal() == findVal && val5.getVal() == 0 && val8.getVal() == 0) {
			arr[0] = 5;
			arr[1] = 8;
			return arr[rand.nextInt(2)];
		} else if (val2.getVal() == 0 && val5.getVal() == 0 && val8.getVal() == findVal) {
			arr[0] = 2;
			arr[1] = 5;
			return arr[rand.nextInt(2)];
		} else if (val2.getVal() == 0 && val5.getVal() == findVal && val8.getVal() == 0) {
			arr[0] = 2;
			arr[1] = 8;
			return arr[rand.nextInt(2)];
		}
		
		// Third col
		if (val3.getVal() == findVal && val6.getVal() == 0 && val9.getVal() == 0) {
			arr[0] = 6;
			arr[1] = 9;
			return arr[rand.nextInt(2)];
		} else if (val3.getVal() == 0 && val6.getVal() == 0 && val9.getVal() == findVal) {
			arr[0] = 3;
			arr[1] = 6;
			return arr[rand.nextInt(2)];
		} else if (val3.getVal() == 0 && val6.getVal() == findVal && val9.getVal() == 0) {
			arr[0] = 3;
			arr[1] = 9;
			return arr[rand.nextInt(2)];
		}
		return -1;
	}
	
	private int findOneInDiag(int findVal) {
		int[] arr = new int[2];
		
		// Main diag
		if (val1.getVal() == findVal && val5.getVal() == 0 && val9.getVal() == 0) {
			arr[0] = 5;
			arr[1] = 9;
			return arr[rand.nextInt(2)];
		} else if (val1.getVal() == 0 && val5.getVal() == 0 && val9.getVal() == findVal) {
			arr[0] = 1;
			arr[1] = 5;
			return arr[rand.nextInt(2)];
		} else if (val1.getVal() == 0 && val5.getVal() == findVal && val9.getVal() == 0) {
			arr[0] = 1;
			arr[1] = 9;
			return arr[rand.nextInt(2)];
		}
		
		// Minor diag
		if (val3.getVal() == findVal && val5.getVal() == 0 && val7.getVal() == 0) {
			arr[0] = 5;
			arr[1] = 7;
			return arr[rand.nextInt(2)];
		} else if (val3.getVal() == 0 && val5.getVal() == 0 && val7.getVal() == findVal) {
			arr[0] = 3;
			arr[1] = 5;
			return arr[rand.nextInt(2)];
		} else if (val3.getVal() == 0 && val5.getVal() == findVal && val7.getVal() == 0) {
			arr[0] = 3;
			arr[1] = 7;
			return arr[rand.nextInt(2)];
		}
		return -1;
	}
	
	private int findTwoInRow(int findVal) {
		
		// First row
		if (val1.getVal() == findVal && val2.getVal() == findVal && val3.getVal() == 0) {
			return 3;
		} else if (val1.getVal() == findVal && val2.getVal() == 0 && val3.getVal() == findVal) {
			return 2;
		} else if (val1.getVal() == 0 && val2.getVal() == findVal && val3.getVal() == findVal) {
			return 1;
		}
		
		// Second row
		if (val4.getVal() == findVal && val5.getVal() == findVal && val6.getVal() == 0) {
			return 6;
		} else if (val4.getVal() == findVal && val5.getVal() == 0 && val6.getVal() == findVal) {
			return 5;
		} else if (val4.getVal() == 0 && val5.getVal() == findVal && val6.getVal() == findVal) {
			return 4;
		}
		
		// Third row
		if (val7.getVal() == findVal && val8.getVal() == findVal && val9.getVal() == 0) {
			return 9;
		} else if (val7.getVal() == findVal && val8.getVal() == 0 && val9.getVal() == findVal) {
			return 8;
		} else if (val7.getVal() == 0 && val8.getVal() == findVal && val9.getVal() == findVal) {
			return 7;
		}
		return -1;
	}
	
	private int findTwoInCol(int findVal) {
		
		// First col
		if (val1.getVal() == findVal && val4.getVal() == findVal && val7.getVal() == 0) {
			return 7;
		} else if (val1.getVal() == findVal && val4.getVal() == 0 && val7.getVal() == findVal) {
			return 4;
		} else if (val1.getVal() == 0 && val4.getVal() == findVal && val7.getVal() == findVal) {
			return 1;
		}
		
		// Second col
		if (val2.getVal() == findVal && val5.getVal() == findVal && val8.getVal() == 0) {
			return 8;
		} else if (val2.getVal() == findVal && val5.getVal() == 0 && val8.getVal() == findVal) {
			return 5;
		} else if (val2.getVal() == 0 && val5.getVal() == findVal && val8.getVal() == findVal) {
			return 2;
		}
		
		// Third col
		if (val3.getVal() == findVal && val6.getVal() == findVal && val9.getVal() == 0) {
			return 9;
		} else if (val3.getVal() == findVal && val6.getVal() == 0 && val9.getVal() == findVal) {
			return 6;
		} else if (val3.getVal() == 0 && val6.getVal() == findVal && val9.getVal() == findVal) {
			return 3;
		}
		return -1;
	}
	
	private int findTwoInDiag(int findVal) {
		
		// Main diag
		if (val1.getVal() == findVal && val5.getVal() == findVal && val9.getVal() == 0) {
			return 9;
		} else if (val1.getVal() == findVal && val5.getVal() == 0 && val9.getVal() == findVal) {
			return 5;
		} else if (val1.getVal() == 0 && val5.getVal() == findVal && val9.getVal() == findVal) {
			return 1;
		}
		
		// Minor diag
		if (val3.getVal() == findVal && val5.getVal() == findVal && val7.getVal() == 0) {
			return 7;
		} else if (val3.getVal() == findVal && val5.getVal() == 0 && val7.getVal() == findVal) {
			return 5;
		} else if (val3.getVal() == 0 && val5.getVal() == findVal && val7.getVal() == findVal) {
			return 3;
		}
		return -1;
	}
	
	private int findEasySpot() {
		int rowSpot = findOpenRowSpot();
		int colSpot = findOpenColSpot();
		
		if (rowSpot != -1) {
			return rowSpot;
		}
		
		if (colSpot != -1) {
			return colSpot;
		}
		
		return -1;
	}
	
	private int findRandomSpot() {
		ArrayList<Integer> openSpots = new ArrayList<>();
		
		for (int i = 1; i <= 9; i++) {
			if (gameBoard.getTileAt(i) != null && gameBoard.getTileAt(i).getValue() == 0) {
				openSpots.add(i);
			}
		}
		
		if (openSpots.size() > 0) {
			return openSpots.get(rand.nextInt(openSpots.size()));
		}
		return -1;
	}
	
	private int findOpenRowSpot() {
		
		if (val1.getVal() == 0 && val2.getVal() == 0 && val3.getVal() == 0) {
			return 1;
		}
		
		if (val4.getVal() == 0 && val5.getVal() == 0 && val6.getVal() == 0) {
			return 6;
		}
		
		if (val7.getVal() == 0 && val8.getVal() == 0 && val9.getVal() == 0) {
			return 8;
		}
		return -1;
	}
	
	private int findOpenColSpot() {
		
		if (val1.getVal() == 0 && val4.getVal() == 0 && val7.getVal() == 0) {
			return 1;
		}
		
		if (val2.getVal() == 0 && val5.getVal() == 0 && val8.getVal() == 0) {
			return 8;
		}
		
		if (val3.getVal() == 0 && val6.getVal() == 0 && val9.getVal() == 0) {
			return 6;
		}
		return -1;
	}

}
