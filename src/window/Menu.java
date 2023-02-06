package window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import framework.MusicPlayer;

/**
 * This is the menu class which represents the main menu of the game.
 * The user comes across the main menu right after starting the game.
 * From the main menu, the user has a choice to either play the game, get instructions, or to quit the game.
 * @author Aleksandar Spasic
 *
 */
public class Menu extends MouseAdapter{

	private BufferedImageLoader imageLoader = new BufferedImageLoader();
	private BufferedImage image;
	
	public static MusicPlayer musicPlayer = new MusicPlayer();
	
	public Menu(){
		this.image = imageLoader.loadImage("res/GokuVSFrieza.jpg");
		musicPlayer.loop("res/GohanPowersUp.wav");
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, (Game.WIDTH/2)-50, 150, 150, 65)){
			Game.gameState = Game.STATE.Game;
			musicPlayer.stop();
			musicPlayer.loop("res/VegitoBirth.wav");
		}
		
		if(mouseOver(mx, my, (Game.WIDTH/2)-50, 250, 150, 65)){
			Game.gameState = Game.STATE.Help;
			musicPlayer.stop();
			musicPlayer.loop("res/HelpScreen.wav");
		}
		
		if(mouseOver(mx, my, (Game.WIDTH/2)-50, 350, 150, 65)){
			System.exit(0);
		}
	}
	
	public void mouseReleased(MouseEvent e){}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx > x && mx < x + width){
			if(my > y && my < y + height){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public void tick(){}
	
	public void render(Graphics g){
		Font font = new Font("arial", 1, 50);
		Font font2 = new Font("arial", 1, 30);
		
		g.drawImage(image, 0, 0, 850, 650, null);
		
		g.setFont(font);
		
		g.setColor(Color.BLACK);
		g.drawString("Capstone Fighter", 250, 120);
		g.setFont(font2);
		
		g.drawRect((Game.WIDTH/2)-45, 150, 150, 65);
		g.drawString("Play", (Game.WIDTH/2)+5, 192);
		
		g.drawRect((Game.WIDTH/2)-45, 250, 150, 65);
		g.drawString("Help", (Game.WIDTH/2)+5, 292);
		
		g.drawRect((Game.WIDTH/2)-45, 350, 150, 65);
		g.drawString("Quit", (Game.WIDTH/2)+5, 392);
	}
}
