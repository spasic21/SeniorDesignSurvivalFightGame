package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This is the HelpScreen class which represents the help screen in the game.
 * The help screen is accessed from the main menu.
 * It gives brief background information to the user and tells them how to play.
 * 
 * @author Aleksandar Spasic
 *
 */
public class HelpScreen {
	
	private BufferedImageLoader imageLoader = new BufferedImageLoader();
	private BufferedImage image;
	
	private String helpText1 = "Aliens are invading a small town.";
	private String helpText2 = "You have to destroy them before they destroy the town!";
	private String helpText3 = "To move around, use the WASD keys.";
	private String helpText4 = "To hit the aliens, use the J key.";
	private String helpText5 = "To go back to the Main Menu, use the Backspace key.";
	
	public HelpScreen(){
		this.image = imageLoader.loadImage("/GokuFriezaHelpScreen.jpg");
	}
	
	public void tick(){}
	
	public void render(Graphics g){
		Font font = new Font("arial", 1, 50);
		Font font2 = new Font("arial", 1, 14);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Help", 40, 120);
		
		g.setFont(font2);
		g.drawString(helpText1, 10, 150);
		g.drawString(helpText2, 10, 170);
		g.drawString(helpText3, 25, 200);
		g.drawString(helpText4, 25, 220);
		g.drawString(helpText5, 25, 240);
		
		g.drawImage(image, 425, 0, 400, Game.HEIGHT, null);
	}
}
