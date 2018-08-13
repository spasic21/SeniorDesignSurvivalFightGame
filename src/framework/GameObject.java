package framework;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

/**
 * This class was used as a parent class to all of the game characters used in this game.
 * This class tracked the game objects position, speed, updating and rendering methods, and collision bounds.
 * 
 * @author Aleksandar Spasic
 *
 */
public abstract class GameObject {
	
	protected float x, y;
	protected ObjectId id;
	protected float velX = 0, velY = 0;
	protected String action = "No Action";
	
	public GameObject(float x, float y, ObjectId id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick(List<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();	
	
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
	
	public float getVelX(){
		return velX;
	}
	
	public float getVelY(){
		return velY;
	}
	
	public void setVelX(float velX){
		this.velX = velX;
	}
	
	public void setVelY(float velY){
		this.velY = velY;
	}

	public ObjectId getId(){
		return id;
	}
	
	public void setAction(String action){
		this.action = action;
	}
	
	public String getAction(){
		return action;
	}
}
