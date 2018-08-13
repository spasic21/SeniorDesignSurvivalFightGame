package window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import objects.Baddie;
import objects.Player;

/**
 * This class is used to animate the character sprites that are in this game.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Animation {

	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage... args){
		this.speed = speed;
		images = new BufferedImage[args.length];
		
		for(int i = 0; i < args.length; i++){
			images[i] = args[i];
		}
		
		frames = images.length;
	}
	
	public void runAnimation(){
		index++;
		
		if(index >= speed){
			index = 0;
			nextFrame();
		}
	}
	
	private void nextFrame(){
		if(count >= frames){
			count = 0;
			
			if(Player.playerState == Player.PlayerState.Action) Player.playerState = Player.PlayerState.Idle_Walking;
			if(Baddie.baddieState == Baddie.BaddieState.Hit) Baddie.baddieState = Baddie.BaddieState.Idle_Walking;
		}
		
		for(int i = 0; i < frames; i++){
			if(count == i){
				currentImg = images[i];
			}
		}
		
		count++;
	}
	
	public void drawAnimation(Graphics g, int x, int y){
		g.drawImage(currentImg, x, y, null);
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);
	}
}
