package TilesFX;

//this class is to load and split all sprites on start up

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ContentFX {
	

	public static BufferedImage[][] ITEMS = load("/SpritesFX/items.gif", 16, 16); //the map has 16 rows and 16 columns
	public static BufferedImage[][] PLAYER = load("/SpritesFX/playersprites.gif", 16, 16);
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(ContentFX.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics."); //this will be printed if the map cannot load
			System.exit(0);
		}
		return null;
	}
	

	
}
