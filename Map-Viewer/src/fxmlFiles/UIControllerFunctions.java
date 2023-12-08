package fxmlFiles;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.awt.image.BufferedImage;
import java.util.Optional;

import TilesFX.ContentFX;
import TilesFX.TileMapFX;


public class UIControllerFunctions {
	TileMapFX tileMap = new TileMapFX(16); //16 is the tile size
	int[][] map;
	int[][] mapStatus= new int[40][40]; //the map size is 40 by 40
	int NUM_COL = 40;
	int NUM_ROW = 40;
	
	StatusGetters getStatus;
	
	//prevent multiple instance of the same objects being used for the same purpose
	public UIControllerFunctions(StatusGetters getStatus) {
		this.getStatus=getStatus;
	}
	
	//to load map
	public void loadMap(GridPane grid, Label reminder) {
		tileMap.loadTiles("/TilesetsFX/testtileset.gif"); // image for the map that will be extracted into pieces to create the map based on the numbers in testmap
		tileMap.loadMap("/MapsFX/testmap.map"); //every number in testmap.map represents the image on the tile so we use the number on the testmap to create the map
		map = tileMap.getMap();
		for (int row = 0; row < NUM_ROW; row++) {
			for (int col = 0; col < NUM_COL; col++) {
				tileMap.generateOneTileByMap(grid, row, col);
				//put player image on 17 by 17
				if (row==17&&col==17) {
					HBox imageField = new HBox();
					imageField.setAlignment(Pos.CENTER);
					grid.add(imageField, row, col);

					BufferedImage PlayerBuf = ContentFX.PLAYER[0][0];
					Image PlayerImage = SwingFXUtils.toFXImage(PlayerBuf, null);
					imageField.getChildren().add(new ImageView(PlayerImage));	

				}
			}

		}
		
		// tell user that load map successful & to click on the boat and axe
		reminder.setText("Successfully loaded map!\n\n Set Axe/Boat now!");

	}
	
	//set axe position on the map and save the coordinates on txt file
	public void setAxe(GridPane grid, Label reminder, Label cordsAxe) {
	
		String filePath = "/DiamondHunter/SettingFile/axe.txt";
		
		StatusGetters.showReminder(reminder, "Please click a position \n to input axe!");
		
		capturePutAxe(filePath, grid, reminder, cordsAxe);	
	}
	
	//set boat position on the map and save the coordinates on txt file
	public void setBoat(GridPane grid, Label reminder, Label cordsBoat ) {
	   
		String filePath = "/DiamondHunter/SettingFile/boat.txt"; //to be changed later
		
		StatusGetters.showReminder(reminder, "Please click a position \n to input boat!");
		
		capturePutBoat(filePath, grid, reminder, cordsBoat); 
	}
	
	//place axe on the map only if the tile is not at player, water or tree coordinates
	public void capturePutAxe(String filePath, GridPane grid, Label reminder, Label cordsAxe) {
		grid.setOnMouseClicked(e -> {
			int getX = (int) e.getX()/tileMap.getTileSize();
			int getY = (int) e.getY()/tileMap.getTileSize();
			int [] clicked = {getX,getY};
		
			 if (map[getY][getX]==20 || map[getY][getX]==21) {
				  StatusGetters.showReminder(reminder,"Unable to set axe \n at tree position."); 
			 } 
			else if (map[getY][getX]== 22) {
				  StatusGetters.showReminder(reminder, "Unable to set axe \n at water position."); 
			} 
			else if(getStatus.isPlayerCords(clicked)) {
				  StatusGetters.showReminder(reminder, "Unable to set axe \n at player position."); 
			}
			else if(getStatus.isAxeCords(clicked)) {
				  StatusGetters.showReminder(reminder, "You have put axe at \n this position here."); 
			}
			else if(getStatus.isBoatCords(clicked)) {
				  StatusGetters.showReminder(reminder, "Unable to set axe \n at boat position."); 
			}
			else {  
				  this.clearLastAxe(grid); // removes previous axe image, if any
				  StatusGetters.writePositionToFile(filePath, getX, getY); //put coordinates in the text file
				  getStatus.generateAxeOnMap(grid, getX, getY); // display the axe on the map
				  StatusGetters.showReminder(reminder, "Set Axe Successfully!"); // shows message
				  
				  //put here that it prints the coordinate of the axe in a message
				  getStatus.getAxeCords(getX, getY, cordsAxe);
			}

			
			
			grid.setOnMouseClicked(null);
			
		});

	}

	//place boat on the map only if the tile is not at player, water or tree coordinates
	public void capturePutBoat(String filePath, GridPane grid, Label reminder, Label cordsBoat) {
		
		grid.setOnMouseClicked(e -> {
			int getX = (int) e.getX()/tileMap.getTileSize();
			int getY = (int) e.getY()/tileMap.getTileSize();
			int [] clicked = {getX,getY};
		
			 if (map[getY][getX]==20 || map[getY][getX]==21) {
			 
				  System.out.println("Unable to set boat at tree position.");
				  StatusGetters.showReminder(reminder,"Unable to set boat \n at tree position."); 
				  reminder.setText("Unable to set boat \n at tree position");
			 } 
			else if (map[getY][getX]== 22) {
				  StatusGetters.showReminder(reminder, "Unable to set boat \n at water position."); 
			} 
			else if(getStatus.isPlayerCords(clicked)) {
				  StatusGetters.showReminder(reminder, "Unable to set boat \n at player position."); 
			}
			else if(getStatus.isAxeCords(clicked)) {
				  StatusGetters.showReminder(reminder, "Unable to set boat \n at axe position."); 
			}
			else if(getStatus.isBoatCords(clicked)) {
				  StatusGetters.showReminder(reminder, "You have put boat \n at this position here."); 
			}
			else {  
				  this.clearLastBoat(grid); //clear current position before saving
				  StatusGetters.writePositionToFile(filePath, getX, getY);
				  getStatus.generateBoatOnMap(grid, getX, getY); // to display the boat on the desired location
				  StatusGetters.showReminder(reminder, "Set Boat Successfully!");
				  
				//put here that it prints the coordinate of the boat in a message
				  getStatus.getBoatCords(getX, getY, cordsBoat);
			}
			
			grid.setOnMouseClicked(null);
			
		});
	}
	
	//clear last axe when user placed new axe
	public void clearLastAxe(GridPane grid) {
		
		int [] temp_axe = new int [2];
		temp_axe = getStatus.getAxeCords();
		
		tileMap.generateOneTileByMap(grid, temp_axe[0], temp_axe[1]);
	}

	//clear last boat when user placed new boat
	public void clearLastBoat(GridPane grid) {
		
		int [] temp_boat = new int [2];
		temp_boat = getStatus.getBoatCords();
		tileMap.generateOneTileByMap(grid, temp_boat[0], temp_boat[1]);
	}
	
	//when hover around the map, get the coordinates
	public void setHoverCords(GridPane grid, Label cords, MouseEvent hover) {
		int getX = (int) hover.getX()/tileMap.getTileSize();
		int getY = (int) hover.getY()/tileMap.getTileSize();
		if (getX>=40) { //error capture for array out of index
			getX=39;
		}
		if (getY>=40) {
			getY=39;
		}
		getStatus.getCurrCords(getX, getY, tileStatusfree(getX, getY), grid, cords, hover);
		
	}
	
	//if the map is not water, player, tree, axe or boat coordinates - the tile is considered free
	public boolean tileStatusfree(int getX, int getY) {// x is row, y is column, not the other way around
		int [] cord = {getX, getY}; //coordinated x and y
		
		if (map[getY][getX]==1||map[getY][getX]==2||map[getY][getX]==3) { //map y and x
			if ( !getStatus.isAxeCords(cord) && !getStatus.isBoatCords(cord) && !getStatus.isPlayerCords(cord)) {
				return true;
			}
		}
			return false;
	}
	
	//when the user clicked reset it will give an alert as below and clear axe and boat on the map & axe
	//and boat will be placed on default position
	public void resetHandler(GridPane grid, Label cordsAxe, Label cordsBoat) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("WARNING: THIS CANNOT BE UNDONE");
		alert.setContentText("This is a Factory Reset and your Diamond Hunter\nwill revert back to factory settings.");
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			this.clearLastAxe(grid);
			this.clearLastBoat(grid);
		    getStatus.factoryReset(grid); //reset the map to default
			getStatus.getAxeCords(getStatus.getAxeCords()[0], getStatus.getAxeCords()[1], cordsAxe); //update axe label
			getStatus.getBoatCords(getStatus.getBoatCords()[0], getStatus.getBoatCords()[1], cordsBoat); //update boat label

		} else {
		    alert.close();
		}
		
		
	}
	
}