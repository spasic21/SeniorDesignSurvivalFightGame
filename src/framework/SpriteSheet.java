package framework;

import java.awt.image.BufferedImage;

/**
 * This class is used to load in character sprite sheets,
 * which are used for character animations
 * 
 * @author Aleksandar Spasic
 *
 */
public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height){
		BufferedImage img = image.getSubimage((col*width)-width, (row*height)-height, width, height);
		
		return img;
	}
}
