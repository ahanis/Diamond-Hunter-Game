package TilesFX;

//The tile map class contains a loaded tile set
//and a 2d array of the map.
//Each index in the map corresponds to a specific tile.
//Naming convention to prevent conflict with DimaondHunter Module
//

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

//this is to generate the entire map image

public class TileMapFX {

	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private TileFX[][] tiles;

	// image
	private Image tileImage;

	public TileMapFX(int tileSize) {
		this.tileSize = tileSize;

	}

	public void loadTiles(String s) {

		try {

			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;

			tiles = new TileFX[2][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new TileFX(subimage, TileFX.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new TileFX(subimage, TileFX.BLOCKED);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String filepath) {// copy from PlayState of Diamond Hunter, no more 

		try {

			InputStream in = getClass().getResourceAsStream(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * generate a image of a specific tile
	 * @param row
	 * @param col
	 */
	public void generateImage(int col, int row) {

		int rc = map[col][row];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		tileImage = SwingFXUtils.toFXImage(tiles[r][c].getImage(), null);
	}

	/**
	 * load the new tile to grid pane 
	 * @param grid
	 * @param row
	 * @param col
	 */
	public void generateOneTileByMap(GridPane grid, int row, int col) {

		HBox tileField = new HBox();
		tileField.setAlignment(Pos.CENTER);
		grid.add(tileField, row, col);

		ImageView tilePane = new ImageView();
		tileField.getChildren().add(tilePane);
		generateImage(col, row);
		tilePane.setImage(getTileImage());
	}

	public Image getTileImage() {
		return tileImage;
	}

	public int getNumTilesAcross() {
		return numTilesAcross;
	}

	public int[][] getMap() {
		return map;
	}

	public TileFX[][] getTiles() {
		return tiles;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	public int getIndex(int row, int col) {
		return map[row][col];
	}

	public void setTile(int row, int col, int index) {
		map[row][col] = index;
	}

}
