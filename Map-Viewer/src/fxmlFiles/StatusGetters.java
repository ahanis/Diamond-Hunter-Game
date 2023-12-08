package fxmlFiles;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import TilesFX.ContentFX;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

//this class contains functions to run map viewer

public class StatusGetters {
	
	int NUM_ROW = 40;
	int NUM_COL = 40;
	int[] CursorCords = new int[2]; //two because row index and column index
	int[] AxeCords = new int [2];
	int[] BoatCords = new int [2];
	static boolean loadCords = false;
	int [] PlayerCords = {17, 17};
	static final 	int[] defaultAxeCords = {37, 26};
	static final int[] defaultBoatCords = {4, 12};
	String coordinates ="Co-ordinates- X: %d, Y: %d\nStatus: %s"; //mouse hover coordinates and status of the tiles
																  //whether it is blocked by the trees and water or free
	
	//display chosen coordinates of axe and boat in labels
	String chosen_coordinates_axe = "Axe co-ordinates: \nX: %d, Y: %d\n";
	String chosen_coordinates_boat = "Boat co-ordinates: \n X: %d, Y: %d\n";
	
	public StatusGetters() {	
	}
	
	public static void showReminder(Label reminder, String message) { //showing the message of the game after any actions e.g successfully loaded map, 
																	  // unable to place axe on player coordinates and etc
		reminder.setText(message);
	}
	
	//load image of axe on the tile
	// only called upon if placing axe is successful, then update axe coordinates
	public void generateAxeOnMap(GridPane grid, int rowIndex, int colIndex) {
		HBox imageField = new HBox();
		imageField.setAlignment(Pos.CENTER);
		grid.add(imageField, rowIndex, colIndex);
		BufferedImage axeBuf = ContentFX.ITEMS[1][1];
		Image axeImage = SwingFXUtils.toFXImage(axeBuf, null);
		imageField.getChildren().add(new ImageView(axeImage));
	}
	
	//load image of boat on the tile
	//only called upon if placing boat is successful, then update boat coordinates
	public void generateBoatOnMap(GridPane grid, int rowIndex, int colIndex) {
		HBox imageField = new HBox();
		imageField.setAlignment(Pos.CENTER);
		grid.add(imageField, rowIndex, colIndex);
		BufferedImage boatBuf = ContentFX.ITEMS[1][0];
		Image boatImage = SwingFXUtils.toFXImage(boatBuf, null);
		imageField.getChildren().add(new ImageView(boatImage));	
	}

	//print the coordinates on the file
	public static void writePositionToFile(String filePath, int rowIndex, int colIndex) {
		try {
			File file = new File(filePath);

			// if file does not exists, then create it
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			PrintStream ps = new PrintStream(filePath);
			ps.println(rowIndex);
			ps.println(colIndex);

			ps.close();

		} catch (IOException x) {
			System.out.println("Error: " + x);
			x.printStackTrace();
		}
	}
	
	//get current coordinates when the mouse hover
	public int[] getCurrCords(int getX, int getY, boolean status, GridPane grid, Label cords, MouseEvent hover) {
		CursorCords[0] = getX;
		CursorCords[1] =  getY;
		setCurrCordsText(cords, status ,CursorCords[0],CursorCords[1]);

		return CursorCords;
	}
	
	//write the status on map viewer application the state of the tile based on the mouse hover
	//if the tile is blocked by tree or water, set up the status to blocked otherwise free
	public void setCurrCordsText(Label cords, boolean status, int getX, int getY) {
		if (status) {
			cords.setText(String.format(coordinates, getX, getY,"free"));
		}else {
			cords.setText(String.format(coordinates, getX, getY,"blocked"));
		}
			
	}
	
	//get the axe coordinates once the axe is placed
	public int[] getAxeCords(int getX, int getY, Label cordsAxe)
	{
		AxeCords[0] = getX;
		AxeCords[1] = getY;
		displayCordsAxeText(cordsAxe, AxeCords[0], AxeCords[1]);
		return AxeCords;
	}

	//to display coordinates of  current axe in the map viewer application
	private void displayCordsAxeText(Label cordsAxe, int getX, int getY) {
		cordsAxe.setText(String.format(chosen_coordinates_axe, getX, getY));
	}
	
	//get the boat coordinates once the boat is placed
	public int[] getBoatCords(int getX, int getY, Label cordsBoat)
	{
		BoatCords[0] = getX;
		BoatCords[1] = getY;
		displayCordsBoatText(cordsBoat, BoatCords[0], BoatCords[1]);
		return BoatCords;
	}
	
	//to display coordinates of  current boat in the map viewer application
	private void displayCordsBoatText(Label cordsBoat, int getX, int getY) {
		cordsBoat.setText(String.format(chosen_coordinates_boat, getX, getY));
	}

	//to check whether the tile is player tile or not
	public boolean isPlayerCords(int [] cord) {
		if (cord [0]==PlayerCords[0] && cord[1]==PlayerCords[1]) {
			return true;
		}else {
		return false;	
		}
	}
	
	//to check whether the tile is axe tile or not
	public boolean isAxeCords(int [] cord) {
		if (cord [0]==AxeCords[0] && cord[1]==AxeCords[1]) {
			return true;
		}else {
		return false;	
		}
	}
	
	//to check whether the tile is boat tile or not
	public boolean isBoatCords(int [] cord) {
		if (cord [0]==BoatCords[0] && cord[1]==BoatCords[1]) {
			return true;
		}else {
		return false;	
		}
	}
	
	//to get the default axe coordinates which was declared above 37,26
	public int[] getDefaultAxeCords() {
		return defaultAxeCords;
	}
	
	//to get the default boat coordinates which was declared above 4,12
	public int[] getDefaultBoatCords() {
		return defaultBoatCords;
	}
	
	//get the axe coordinates that is being placed by user
	public int[] getAxeCords() {
		return AxeCords;
	}
	
	//get the boat coordinates that is being placed by user
	public int[] getBoatCords() {
		return BoatCords;
	}
	
	//reset to default axe and boat coordinates 
	public void factoryReset(GridPane grid) {
		String axePos = "/DiamondHunter/SettingFile/axe.txt";
		
		String boatPos = "/DiamondHunter/SettingFile/boat.txt";
		
		AxeCords = defaultAxeCords.clone();
		BoatCords = defaultBoatCords.clone(); //prevent overwriting default/factory setting
		
		StatusGetters.writePositionToFile(axePos, AxeCords[0], AxeCords[1]); //write a file into existence
		StatusGetters.writePositionToFile(boatPos, BoatCords[0], BoatCords[1]); 
		this.generateAxeOnMap(grid, AxeCords[0], AxeCords[1]);
		this.generateBoatOnMap(grid, BoatCords[0], BoatCords[1]);
	}
	
	//display last saved coordinates
	//based on what is written in root file
	//if file does not exist write create file with default position
	public void setLastSavedCords(GridPane grid) {
		String axePos = "/DiamondHunter/SettingFile/axe.txt";
		File fileAxe = new File(axePos);
		
		String boatPos = "/DiamondHunter/SettingFile/boat.txt";
		File fileBoat = new File(boatPos);
		
		if (fileAxe.exists() && fileBoat.exists()){
			AxeCords = readPositionFromFile(fileAxe);
			BoatCords = readPositionFromFile(fileBoat);;
		}else {
			this.factoryReset(grid);
			
		}
			this.generateAxeOnMap(grid, AxeCords[0], AxeCords[1]);
			this.generateBoatOnMap(grid, BoatCords[0], BoatCords[1]);
	}
	
	//read coordinates of items from file
	//file path is to get the coordinates of the items
	private int[] readPositionFromFile(File file) {
		int [] pos = new int [2];
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			pos[0] = Integer.parseInt(br.readLine());
			pos[1] = Integer.parseInt(br.readLine());
			br.close(); //close reader when not called
			return pos; //return coordinates of item

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	
}