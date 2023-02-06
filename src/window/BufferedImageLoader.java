package window;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
			image = ImageIO.read(new FileInputStream(path));
		}catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return image;
	}
}
