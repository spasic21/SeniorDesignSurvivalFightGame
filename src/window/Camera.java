package window;

import framework.GameObject;

/**
 * This is a camera class that is used to move with the player 
 * as the player moves around the map.
 * @author Aleksandar Spasic
 *
 */
public class Camera {

	private float x, y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject player){
		x = -player.getX() + Game.WIDTH/2 - 65;
		y = -player.getY() + Game.HEIGHT/2 - 90;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
}
