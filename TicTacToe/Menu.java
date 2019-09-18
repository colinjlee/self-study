package tictactoe;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Menu extends Application {
	private GameBox gbox;
	
	private Stage stage;
	private Scene sceneMenu, sceneSettings, sceneAIDifficulty, sceneGame;
	private Button btnSettings, btnPlayer, btnAI, btnAIE, btnAIM, btnAIH, btnReset,
				   btnMenu1, btnMenu2, btnMenu3, btnBrowse1, btnBrowse2, btnBrowse3;
	private Label p1PicLabel, p2PicLabel, aiPicLabel,
				  p1SourceLabel, p2SourceLabel, aiSourceLabel;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		
		// Label initializing
		p1PicLabel = new Label("Player 1 picture");
		p2PicLabel = new Label("Player 2 picture");
		aiPicLabel = new Label("A.I. picture");
		p1SourceLabel = new Label("tictactoe_X.png");
		p2SourceLabel = new Label("tictactoe_O.png");
		aiSourceLabel = new Label("tictactoe_O.png");
		
		// Label font sizing
		p1SourceLabel.setFont(Font.font(10));
		p2SourceLabel.setFont(Font.font(10));
		aiSourceLabel.setFont(Font.font(10));
		
		p1SourceLabel.setMaxWidth(125);
		p2SourceLabel.setMaxWidth(125);
		aiSourceLabel.setMaxWidth(125);
		
		p1SourceLabel.setAlignment(Pos.CENTER);
		p2SourceLabel.setAlignment(Pos.CENTER);
		aiSourceLabel.setAlignment(Pos.CENTER);
		
		// Button initializing
		btnReset = new Button("Reset");
		btnMenu1 = new Button("Menu");
		btnMenu2 = new Button("Menu");
		btnMenu3 = new Button("Menu");
		btnSettings = new Button("Settings");
		btnPlayer = new Button("2-Player");
		btnAI = new Button("A.I.");
		btnAIE = new Button("Easy");
		btnAIM = new Button("Medium");
		btnAIH = new Button("Hard");
		btnBrowse1 = new Button("Browse");
		btnBrowse2 = new Button("Browse");
		btnBrowse3 = new Button("Browse");
		
		// Layout initializing
		VBox menuButtonLayout = new VBox(20);
		VBox difficultyLayout = new VBox(20);
		VBox settingsLayout = new VBox(15);
		VBox picturesLayout = new VBox(10);
		VBox picturesLayout1 = new VBox(1);
		VBox picturesLayout2 = new VBox(1);
		VBox picturesLayout3 = new VBox(1);
		gbox = new GameBox(0);
		gbox.getBotVBox().getChildren().addAll(btnReset, btnMenu2);
		
		// Scene initializing
		sceneMenu = new Scene(menuButtonLayout, 150, 250);
		sceneAIDifficulty = new Scene(difficultyLayout, 150, 250);
		sceneSettings = new Scene(settingsLayout, 150, 250);
		sceneGame = new Scene(gbox, 800, 600);
		
		// Button sizing
		btnReset.setPrefSize(100, 40);
		btnSettings.setPrefSize(100, 40);
		btnPlayer.setPrefSize(100, 40);
		btnAI.setPrefSize(100, 40);
		btnAIE.setPrefSize(100, 40);
		btnAIM.setPrefSize(100, 40);
		btnAIH.setPrefSize(100, 40);
		btnMenu1.setPrefSize(100, 40);
		btnMenu2.setPrefSize(100, 40);
		btnMenu3.setPrefSize(75, 20);
		btnBrowse1.setPrefSize(75, 20);
		btnBrowse2.setPrefSize(75, 20);
		btnBrowse3.setPrefSize(75, 20);
		
		// Button font sizing
		btnMenu3.setFont(Font.font(10));
		btnBrowse1.setFont(Font.font(10));
		btnBrowse2.setFont(Font.font(10));
		btnBrowse3.setFont(Font.font(10));
		
		// Button actions
		btnReset.setOnAction(e -> {
			gbox.resetBoard();
			
			stage.setScene(sceneGame);
		});
		btnPlayer.setOnAction(e -> {
			gbox.setAIDifficulty(0);
			
			stage.setScene(sceneGame);
			stage.setTitle("Tic-Tac-Toe: 2-Player");
		});
		btnAI.setOnAction(e -> {
			stage.setScene(sceneAIDifficulty);
			stage.setTitle("Tic-Tac-Toe: A.I. Difficulty");
		});
		btnAIE.setOnAction(e -> {
			gbox.setAIDifficulty(1);
			stage.setScene(sceneGame);
			
			stage.setTitle("Tic-Tac-Toe: vs A.I. (Easy)");
		});
		btnAIM.setOnAction(e -> {
			gbox.setAIDifficulty(2);
			stage.setScene(sceneGame);
			
			stage.setTitle("Tic-Tac-Toe: vs A.I. (Medium)");
		});
		btnAIH.setOnAction(e -> {
			gbox.setAIDifficulty(3);
			stage.setScene(sceneGame);
			
			stage.setTitle("Tic-Tac-Toe: vs A.I. (Hard)");
		});
		btnMenu1.setOnAction(e -> {
			stage.setScene(sceneMenu);
			stage.setTitle("Tic-Tac-Toe: Menu");
		});
		btnMenu2.setOnAction(e -> {
			gbox.reset();
			
			stage.setScene(sceneMenu);
			stage.setTitle("Tic-Tac-Toe: Menu");
		});
		btnMenu3.setOnAction(e -> {
			stage.setScene(sceneMenu);
			stage.setTitle("Tic-Tac-Toe: Menu");
		});
		btnSettings.setOnAction(e -> {
			stage.setScene(sceneSettings);
			stage.setTitle("Tic-Tac-Toe: Settings");
		});
		btnBrowse1.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();

	        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
	        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

	        File file = fileChooser.showOpenDialog(null);

	        if (file != null) {
	        	String url = file.toURI().toString();
	            gbox.setP1Image(new Image(url, 200, 200, false, true));
	            p1SourceLabel.setText(url);
	        }
		});
		btnBrowse2.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();

	        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
	        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

	        File file = fileChooser.showOpenDialog(null);

	        if (file != null) {
	        	String url = file.toURI().toString();
	        	gbox.setP2Image(new Image(url, 200, 200, false, true));
	            p2SourceLabel.setText(url);
	        }
		});
		btnBrowse3.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();

	        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
	        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
	        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

	        File file = fileChooser.showOpenDialog(null);

	        if (file != null) {
	        	String url = file.toURI().toString();
	        	gbox.setAIImage(new Image(url, 200, 200, false, true));
	            aiSourceLabel.setText(url);
	        }
		});
		
		// Add buttons to layout
		menuButtonLayout.getChildren().addAll(btnPlayer, btnAI, btnSettings);
		difficultyLayout.getChildren().addAll(btnAIE, btnAIM, btnAIH, btnMenu1);
		picturesLayout.getChildren().addAll(picturesLayout1, picturesLayout2, picturesLayout3);
		picturesLayout1.getChildren().addAll(p1PicLabel, btnBrowse1, p1SourceLabel);
		picturesLayout2.getChildren().addAll(p2PicLabel, btnBrowse2, p2SourceLabel);
		picturesLayout3.getChildren().addAll(aiPicLabel, btnBrowse3, aiSourceLabel);
		settingsLayout.getChildren().addAll(picturesLayout, btnMenu3);
		
		// Center all layout
		menuButtonLayout.setAlignment(Pos.CENTER);
		difficultyLayout.setAlignment(Pos.CENTER);
		picturesLayout.setAlignment(Pos.CENTER);
		picturesLayout1.setAlignment(Pos.CENTER);
		picturesLayout2.setAlignment(Pos.CENTER);
		picturesLayout3.setAlignment(Pos.CENTER);
		settingsLayout.setAlignment(Pos.CENTER);
		
		// Initial stage
		stage.setTitle("Tic-Tac-Toe: Menu");
		stage.setScene(sceneMenu);
		stage.centerOnScreen();
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}

