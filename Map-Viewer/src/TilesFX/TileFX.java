package TilesFX;

import java.awt.image.BufferedImage;

// this class is for map image generation

public class TileFX {
	
	private BufferedImage image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public TileFX(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public BufferedImage getImage() { 
		return image; 
		}
	public int getType() { 
		return type;
		}
	
}
