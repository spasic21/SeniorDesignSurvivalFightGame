package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import window.Animation;
import window.BufferedImageLoader;
import window.Handler;
import framework.GameObject;
import framework.ObjectId;
import framework.SpriteSheet;

/**
 * This is the class to represent the alien in the game.
 * It is inherited from the GameObject class and contains all of its attributes.
 * Such as position, speed, width, height, animations, states, etc.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Baddie extends GameObject{
	
	private float width = 55, height = 64;
	private Map<String, Animation> baddieAnimations = new HashMap<String, Animation>();
	private SpriteSheet bs;
	private BufferedImage baddie_sheet = null;
	private GameObject playerObject;
	private int hitCount = 0;
	private Handler handler;
	public BufferedImage[][] baddieActions = new BufferedImage[4][13];
	public static int direction = 1;
	public static int random = 0;
	
	
	
	public enum BaddieState{
		Idle_Walking,
		Running,
		Action,
		Hit;
	}
	
	public static BaddieState baddieState = BaddieState.Idle_Walking; 
	
	public Baddie(float x, float y, ObjectId id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try{
			baddie_sheet = loader.loadImage("res/FriezaSpriteSheet.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		bs = new SpriteSheet(baddie_sheet);
		
		loadActions();
		loadAnimations();
	}
	
	private void loadActions(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 13; j++){
				baddieActions[i][j] = bs.grabImage(j+1, i+1, 35, 35);
			}
		}
	}
	
	private void loadAnimations(){
		baddieAnimations.put("baddieStandDown", new Animation(10, baddieActions[0][0], baddieActions[0][1], baddieActions[0][0]));
	
		baddieAnimations.put("baddieWalkRight", new Animation(8, baddieActions[1][2], baddieActions[1][3], baddieActions[1][4]));
		baddieAnimations.put("baddieWalkLeft", new Animation(8, baddieActions[2][2], baddieActions[2][3], baddieActions[2][4]));
		baddieAnimations.put("baddieWalkDown", new Animation(8, baddieActions[0][2], baddieActions[0][3], baddieActions[0][4], baddieActions[0][5]));
		baddieAnimations.put("baddieWalkUp", new Animation(8, baddieActions[3][2], baddieActions[3][3], baddieActions[3][4], baddieActions[3][5]));
		
		baddieAnimations.put("baddieHitRight", new Animation(25, baddieActions[1][10], baddieActions[1][11], baddieActions[1][12]));
		baddieAnimations.put("baddieHitLeft", new Animation(25, baddieActions[2][10], baddieActions[2][11], baddieActions[2][12]));
		baddieAnimations.put("baddieHitDown", new Animation(25, baddieActions[3][10], baddieActions[3][11], baddieActions[3][12]));
		baddieAnimations.put("baddieHitUp", new Animation(25, baddieActions[0][10], baddieActions[0][11], baddieActions[0][12]));
	}
	
	private void collisionTick(List<GameObject> object){
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() == ObjectId.Player){
				if(Player.playerState == Player.PlayerState.Action && getBounds().intersects(tempObject.getBounds())){
					baddieState = BaddieState.Hit;
					hitCount++;
					
					if(hitCount > 80){
						handler.removeObject(this);
						hitCount = 0;
						Player.bodyCount++;
					}
				}else if(getBounds().intersects(tempObject.getBounds())){
					Player.playerHealth -= 0.0625;
					
					if(Player.playerHealth <= 0){
						JOptionPane.showMessageDialog(null, "Sorry You Died! Body Count: " + Player.bodyCount);
						System.exit(0);
					}
				}
			}
		}
	}

	public void tick(List<GameObject> object) {
		for(int i = 0; i < object.size(); i++){
			if(object.get(i).getId() == ObjectId.Player) playerObject = object.get(i);
		}
		
		x += velX;
		y += velY;
		
		if(velX > 0) direction = 1;
		else if(velX < 0) direction = 2;
		else if(velY > 0) direction = 3;
		else if(velY < 0) direction = 4;
		
		float diffX = x - playerObject.getX();
		float diffY = y - playerObject.getY();
		float distance = (float) Math.sqrt(diffX*diffX + diffY*diffY);
	
		if(distance < 30){
			velX = 0;
			velY = 0;
		}else{
			velX = (float) ((-2.0/distance) * diffX);
			velY = (float) ((-2.0/distance) * diffY);
		}
		
		collisionTick(object);
		
		if(baddieState == BaddieState.Idle_Walking){
			if(direction == 1){
				baddieAnimations.get("baddieWalkRight").runAnimation();
			}else if(direction == 2){
				baddieAnimations.get("baddieWalkLeft").runAnimation();
			}else if(direction == 3){
				baddieAnimations.get("baddieWalkDown").runAnimation();
			}else if(direction == 4){
				baddieAnimations.get("baddieWalkUp").runAnimation();
			}
		}else if(baddieState == BaddieState.Hit){
			if(Player.direction == 1) baddieAnimations.get("baddieHitLeft").runAnimation();
			else if(Player.direction == 2) baddieAnimations.get("baddieHitRight").runAnimation();
			else if(Player.direction == 3) baddieAnimations.get("baddieHitDown").runAnimation();
			else baddieAnimations.get("baddieHitUp").runAnimation();
		}
	}

	public void render(Graphics g) {
		
		if(baddieState == BaddieState.Idle_Walking){
			if(velX != 0){
				if(direction == 1){
					baddieAnimations.get("baddieWalkRight").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}else if (direction == 2){
					baddieAnimations.get("baddieWalkLeft").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}
			}else if(velY != 0){
				if(direction == 3){
					baddieAnimations.get("baddieWalkDown").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}else if(direction == 4){
					baddieAnimations.get("baddieWalkUp").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}
			}else{
				if(direction == 1){
					g.drawImage(baddieActions[1][0], (int)x, (int)y+10, (int)width, (int)height, null);
				}else if(direction == 2){
					g.drawImage(baddieActions[2][0], (int)x, (int)y+10, (int)width, (int)height, null);
				}else if (direction == 3){
					g.drawImage(baddieActions[0][0], (int)x, (int)y+10, (int)width, (int)height, null);
				}else{
					baddieAnimations.get("baddieStandDown").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}
			}
		}else if(baddieState == BaddieState.Hit){
			if(Player.direction == 1) baddieAnimations.get("baddieHitLeft").drawAnimation(g, (int)x+10, (int)y+10, (int)width, (int)height);
			else if(Player.direction == 2) baddieAnimations.get("baddieHitRight").drawAnimation(g, (int)x-10, (int)y+10, (int)width, (int)height);
			else if(Player.direction == 3) baddieAnimations.get("baddieHitDown").drawAnimation(g, (int)x, (int)y+20, (int)width, (int)height);
			else baddieAnimations.get("baddieHitUp").drawAnimation(g, (int)x, (int)y-10, (int)width, (int)height);
			
//			baddieState = BaddieState.Idle_Walking;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)(x+(width/2)-(width/2)), (int)(y+(height/5)), 55, 50);
	}
	
	public int getDirection(){
		return direction;
	}
}
