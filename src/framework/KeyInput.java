package framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import objects.Player;
import window.Game;
import window.Game.STATE;
import window.Handler;
import window.Menu;

/**
 * This class handled all of the key input that was used in the game.
 * 
 * @author Aleksandar Spasic
 *
 */
public class KeyInput extends KeyAdapter{

	private Handler handler;
	private boolean[] keyDown = new boolean[5];
	
	public KeyInput(Handler handler){
		this.handler = handler;
		
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		keyDown[4] = true;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				if(key == KeyEvent.VK_D) {tempObject.setVelX(4); keyDown[0] = true;}
				if(key == KeyEvent.VK_A) {tempObject.setVelX(-4); keyDown[1] = true;}
				if(key == KeyEvent.VK_S) {tempObject.setVelY(4); keyDown[2] = true;}
				if(key == KeyEvent.VK_W) {tempObject.setVelY(-4); keyDown[3] = true;}
				if(key == KeyEvent.VK_J) keyDown[4] = false;
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		if(key == KeyEvent.VK_BACK_SPACE && Game.gameState == STATE.Help){
			Game.gameState = STATE.Menu;
			Menu.musicPlayer.stop();
			Menu.musicPlayer.play("res/GohanPowersUp.wav");
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				if(key == KeyEvent.VK_D) {
					Player.direction = 1;
					Player.playerState = Player.PlayerState.Idle_Walking;
					keyDown[0] = false;
				}
				if(key == KeyEvent.VK_A) {
					Player.direction = 2;
					keyDown[1] = false;
				}
				if(key == KeyEvent.VK_S){
					Player.direction = 3;
					keyDown[2] = false;
				}
				if(key == KeyEvent.VK_W){
					Player.direction = 4;
					keyDown[3] = false;
				}
				
				if(!keyDown[0] && !keyDown[1]) tempObject.setVelX(0);
				if(!keyDown[2] && !keyDown[3]) tempObject.setVelY(0);
				
				if(key == KeyEvent.VK_J) {
					Player.random = (int)(Math.random() * 3);
					Player.playerState = Player.PlayerState.Action;
					keyDown[4] = true;
				}
			}
		}
	}
}
