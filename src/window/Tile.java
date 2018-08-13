package window;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import framework.GameObject;
import framework.ObjectId;

/**
 * This is the Tile class which represents all of the tiles that are used to create the map.
 * It is inherited from the GameObject class and contains all of its attributes.
 * Such as position, width, height, images, etc.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Tile extends GameObject{
	private int tileWidth = 64, tileHeight = 64;
	private BufferedImage image;
	private Handler handler;
	
	public Tile(float x, float y, ObjectId id, BufferedImage image, Handler handler){
		super(x, y, id);
		this.image = image;
		this.handler = handler;
	}
	
	public void tick(List<GameObject> object){}
	
	public void render(Graphics g){
		g.drawImage(image, (int)x, (int)y, tileWidth, tileHeight, null);
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, tileWidth, tileHeight);
	}
	
	public void collision(){
		for(int i = 0 ; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ObjectId.Player || tempObject.getId() == ObjectId.Baddie){
				if(getBounds().intersects(tempObject.getBounds())){
					if(tempObject.getVelX() > 0){
						tempObject.setX(tempObject.getX() - 5);
						if(tempObject.getVelY() > 0) tempObject.setY(tempObject.getY() - 5);
						else if(tempObject.getVelY() < 0) tempObject.setY(tempObject.getY() + 5);
					}else if(tempObject.getVelX() < 0){
						tempObject.setX(tempObject.getX() + 5);
						if(tempObject.getVelY() > 0) tempObject.setY(tempObject.getY() - 5);
						else if(tempObject.getVelY() < 0) tempObject.setY(tempObject.getY() + 5);
					}else if(tempObject.getVelY() > 0){
						tempObject.setY(tempObject.getY() - 5);
						if(tempObject.getVelX() > 0) tempObject.setX(tempObject.getX() - 5);
						else if(tempObject.getX() < 0) tempObject.setX(tempObject.getX() + 5);
					}else{
						tempObject.setY(tempObject.getY() + 5);
						if(tempObject.getVelX() > 0) tempObject.setX(tempObject.getX() - 5);
						else if(tempObject.getX() < 0) tempObject.setX(tempObject.getX() + 5);
					}
				}
			}
		}
	}
}
