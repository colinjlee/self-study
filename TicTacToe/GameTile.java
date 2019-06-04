package tictactoe;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameTile extends StackPane {
	private ImageView imageView;
	private MutableInt value; // (0) empty (1) player 1, (2) player 2, (3) ai
	private final int TILE_SIZE = 200;
	
	public GameTile() {
		imageView = new ImageView();
		value = new MutableInt(0);
		
		Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
		tile.setFill(Color.WHITE);
		tile.setStroke(Color.BLACK);
		
		setAlignment(Pos.CENTER);
		getChildren().addAll(tile, imageView);
	}
	
	public void reset() {
		imageView.setImage(null);
		value.setVal(0);
	}
	
	public void draw(Image image) {
		if (value.getVal() == 0) {
			imageView.setImage(image);
		}
	}
	
	public int getValue() {
		return value.getVal();
	}
	
	public MutableInt getMutableInt() {
		return value;
	}
	
	public double getCenterX() {
		return getLayoutX() + TILE_SIZE / 2;
	}
	
	public double getCenterY() {
		return getLayoutY() + TILE_SIZE / 2;
	}
	
	public void setValue(int val) {
		value.setVal(val);
	}
}
