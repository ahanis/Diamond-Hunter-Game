package fxmlFiles;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;

import DiamondHunter.Main.MainGame;

//import com.neet.DiamondHunter.Main.Game;

import TilesFX.TileMapFX;
import fxmlFiles.UIControllerFunctions;


public class UIMapviewerController implements UIMVControllerInterface {
	
	StatusGetters getStatus = new StatusGetters();
	UIControllerFunctions functionHolder = new UIControllerFunctions(getStatus);
	int NUM_COL = 40;
	int NUM_ROW = 40;
	
	//make true map loaded once
	boolean hasLoaded = false;
	TileMapFX tileMap = new TileMapFX(16); //16 is the tile size
	MouseEvent cursor;
	int[][] map;
	
	@FXML
	public GridPane grid;
	
	@FXML //these IDs link to the labels in the fxml file
	//reminder is the message to tell the user what to do next
	//cords tells the user if the status of the coordinates is either available or blocked
	//cordsAxe shows the coordinates of axe selected by the user
	//cordsBoat shows the coordinates of boat selected by the user
	
	public Label reminder,cords,cordsAxe,cordsBoat;
	
	@FXML
	public ImageView playButton, resetButton, Logo, placeBoatButton, placeAxeButton;
	
	//axe and boat will be initialized with no coordinates
	//because user needs to input the coordinates
	public void initialize() throws Exception {
		hasLoaded = true;
		functionHolder.loadMap(grid,reminder);
		getStatus.setCurrCordsText(cords,false, 0, 0);
		getStatus.setLastSavedCords(grid);
		int [] AxeCords = getStatus.getAxeCords().clone();
		int [] BoatCords = getStatus.getBoatCords().clone();
		getStatus.getAxeCords(AxeCords[0], AxeCords[1], cordsAxe);
		getStatus.getBoatCords(BoatCords[0], BoatCords[1], cordsBoat);
		this.initializeButtons();
	}
	
	//linking images to the buttons to style the buttons
	public void initializeButtons() throws Exception {
		String playButtonPath= "/Buttons/Play.png";
		initializeImageView(playButtonPath, this.playButton);
		
		String resetButtonPath= "/Buttons/ResetButton.png";
		initializeImageView(resetButtonPath, this.resetButton);
		
		String logoPath= "/Buttons/Diamondhunterlogo.png";
		initializeImageView(logoPath, this.Logo);
		
		String placeAxePath= "/Buttons/PlaceAxe.png";
		initializeImageView(placeAxePath, this.placeAxeButton);
		
		String placeBoatPath= "/Buttons/PlaceBoat.png";
		initializeImageView(placeBoatPath, this.placeBoatButton);
	}
	
	public ImageView initializeImageView(String filepath, ImageView imageView) throws Exception {
		InputStream input = getClass().getResourceAsStream(filepath);
        Image image = new Image(input);
        imageView.setImage(image);
        return imageView;
	}
	
	public void LoadMap() {
		if (hasLoaded == false) {
		hasLoaded = true;
		functionHolder.loadMap(grid,reminder);
		}

	}
	
	//enables the user to hover over a set of coordinates
	//and displays the coordinates in the Label cords
	public void hoverCursor(MouseEvent event) {
		cursor = event;
		functionHolder.setHoverCords(grid, cords, cursor);	
	}
	
	//placing the axe and displaying the chosen coordinates of axe
	public void PlaceAxe() {
		functionHolder.setAxe(grid, reminder, cordsAxe); 
	}
	
	//placing the boat and displaying the chosen coordinates of boat
	public void PlaceBoat() {
		functionHolder.setBoat(grid, reminder, cordsBoat); 
	}
	
	//resets the coordinates of axe and boat
	public void Reset() {
		functionHolder.resetHandler(grid, cordsAxe, cordsBoat);
	}
	
	//starts the game
	public void Play() throws IOException {
		this.howToPlay(); //displays the instructions to play before the game starts
		this.closeApp(); //closes the instructions when user chooses to, so the game can start
		MainGame.main(null);
	}
	
	//displays what the game is about when the user clicks on "About"
	public void aboutInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About"); //message to be displayed
		alert.setHeaderText("About the software & Diamond Hunter");
		alert.setContentText("Diamond Hunter - Version 1.0\n\nDiamond Hunter"
				+ "is a 2D role playing game which the user needs to "
				+ "control the hero to collect all of the 15 diamonds "
				+ "on the map in order to win game. To achieve the goal, "
				+ "the player needs to find axe and boat so that it can "
				+ "open the blocked path. In addition, the map viewer "
				+ "application serve as a purpose for user to overview "
				+ "the whole map and place the axe and boat.");
		alert.showAndWait();
		alert.setOnCloseRequest(event -> {alert.close();});
	}
	
	//message to be displayed when user clicks on "Instruction"
	public void howToPlay() {
		Alert alert = new Alert(AlertType.INFORMATION); //incomplete
		alert.setTitle("Instruction");
		alert.setHeaderText(null);
		alert.setContentText("- Up arrow: move forward\n"
				+ "- Down arrow: move backwards\n"
				+ "- Left arrow: turn left\n"
				+ "- Right Arrow: turn right\n"
				+ "- Enter: start the game\n"
				+ "- Space: to clear dead trees\n"
				+ "- Esc: to pause and unpause \n"
				+ "- F1: to return to main menu when paused\n");
		alert.showAndWait();
		alert.setOnCloseRequest(event -> {alert.close();});
	}
	
	//closes the app when user clicks on "Close"
	public void closeApp() {
		Stage stage = (Stage) grid.getScene().getWindow();
		stage.close();
	}
	
	
}
