package window;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * This class is used to load sprite or regular images into the game.
 * 
 * @author Aleksandar Spasic
 *
 */
public class BufferedImageLoader {

	private BufferedImage image;
	
	public BufferedImage loadImage(String path){
		try{
			image = ImageIO.read(getClass().getResource(path));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return image;
	}
}
