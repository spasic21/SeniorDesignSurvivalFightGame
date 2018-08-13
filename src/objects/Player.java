package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import window.Animation;
import window.BufferedImageLoader;
import framework.GameObject;
import framework.ObjectId;
import framework.SpriteSheet;

/**
 * This is the class to represent the player in the game.
 * It is inherited from the GameObject class and contains all of its attributes.
 * Such as position, speed, width, height, animations, states, etc.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Player extends GameObject{

	private float width = 55, height = 64;
	private Map<String, Animation> playerAnimations = new HashMap<String, Animation>();
	private SpriteSheet ps;
	private BufferedImage goku_sheet = null;
	public BufferedImage[][] playerActions = new BufferedImage[4][35];
	public static int direction = 1;
	public static int random = 0;
	public static float playerHealth = 30;
	public static int bodyCount = 0;
	
	public enum PlayerState{
		Idle_Walking,
		Running,
		Action,
		Hit;
	}
	
	public static PlayerState playerState = PlayerState.Idle_Walking;
	
	public Player(float x, float y, ObjectId id) {
		super(x, y, id);
	
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try{
			goku_sheet = loader.loadImage("/GokuSpriteSheet.png");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		ps = new SpriteSheet(goku_sheet);
		
		loadActions();
		loadAnimations();
	}
	
	private void loadActions(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 35; j++){
				playerActions[i][j] = ps.grabImage(j+1, i+1, 35, 35);
			}
		}
	}
	
	private void loadAnimations(){
		playerAnimations.put("playerStandRight", new Animation(10, playerActions[1][0], playerActions[1][1], playerActions[1][0]));
		playerAnimations.put("playerStandLeft", new Animation(10, playerActions[2][0], playerActions[2][1], playerActions[2][0]));
		playerAnimations.put("playerStandDown", new Animation(10, playerActions[0][0], playerActions[0][1], playerActions[0][0]));
	
		playerAnimations.put("playerWalkRight", new Animation(8, playerActions[1][2], playerActions[1][3], playerActions[1][4], playerActions[1][5]));
		playerAnimations.put("playerWalkLeft", new Animation(8, playerActions[2][2], playerActions[2][3], playerActions[2][4], playerActions[2][5]));
		playerAnimations.put("playerWalkDown", new Animation(8, playerActions[0][2], playerActions[0][3], playerActions[0][4], playerActions[0][5]));
		playerAnimations.put("playerWalkUp", new Animation(8, playerActions[3][2], playerActions[3][3], playerActions[3][4], playerActions[3][5]));
		
		playerAnimations.put("playerPunchRightOne", new Animation(4, playerActions[1][20], playerActions[1][21], playerActions[1][22], playerActions[1][23]));
		playerAnimations.put("playerPunchRightTwo", new Animation(4, playerActions[1][24], playerActions[1][25], playerActions[1][26], playerActions[1][27]));
		playerAnimations.put("playerKickRight", new Animation(4, playerActions[1][28], playerActions[1][29], playerActions[1][30], playerActions[1][31]));
	
		playerAnimations.put("playerPunchLeftOne", new Animation(4, playerActions[2][20], playerActions[2][21], playerActions[2][22], playerActions[2][23]));
		playerAnimations.put("playerPunchLeftTwo", new Animation(4, playerActions[2][24], playerActions[2][25], playerActions[2][26], playerActions[2][27]));
		playerAnimations.put("playerKickLeft", new Animation(4, playerActions[2][28], playerActions[2][29], playerActions[2][30], playerActions[2][31]));
	
		playerAnimations.put("playerPunchDownOne", new Animation(4, playerActions[0][20], playerActions[0][21], playerActions[0][22], playerActions[0][23]));
		playerAnimations.put("playerPunchDownTwo", new Animation(4, playerActions[0][24], playerActions[0][25], playerActions[0][26], playerActions[0][27]));
		playerAnimations.put("playerKickDown", new Animation(4, playerActions[0][28], playerActions[0][29], playerActions[0][30], playerActions[0][31]));
		
		playerAnimations.put("playerPunchUpOne", new Animation(4, playerActions[3][20], playerActions[3][21], playerActions[3][22], playerActions[3][23]));
		playerAnimations.put("playerPunchUpTwo", new Animation(4, playerActions[3][24], playerActions[3][25], playerActions[3][26], playerActions[3][27]));
		playerAnimations.put("playerKickUp", new Animation(4, playerActions[3][28], playerActions[3][29], playerActions[3][30], playerActions[3][31]));
	}
	
	public void tick(List<GameObject> object) {
		x += velX;
		y += velY;
		
		if(velX > 0) direction = 1;
		else if(velX < 0) direction = 2;
		else if(velY > 0) direction = 3;
		else if(velY < 0) direction = 4;
		
		collision(object);
		
		if(playerState == PlayerState.Idle_Walking){
			if(direction == 1){
				playerAnimations.get("playerStandRight").runAnimation();
				playerAnimations.get("playerWalkRight").runAnimation();
			}else if(direction == 2){
				playerAnimations.get("playerStandLeft").runAnimation();
				playerAnimations.get("playerWalkLeft").runAnimation();
			}else if(direction == 3){
				playerAnimations.get("playerStandDown").runAnimation();
				playerAnimations.get("playerWalkDown").runAnimation();
			}else if(direction == 4){
				playerAnimations.get("playerWalkUp").runAnimation();
			}
		}else if(playerState == PlayerState.Action){
			if(direction == 1){
				if(random == 0) playerAnimations.get("playerPunchRightOne").runAnimation();
				else if(random == 1) playerAnimations.get("playerPunchRightTwo").runAnimation();
				else playerAnimations.get("playerKickRight").runAnimation();
			}else if(direction == 2){
				if(random == 0) playerAnimations.get("playerPunchLeftOne").runAnimation();
				else if(random == 1) playerAnimations.get("playerPunchLeftTwo").runAnimation();
				else playerAnimations.get("playerKickLeft").runAnimation();
			}else if(direction == 3) {
				if(random == 0) playerAnimations.get("playerPunchDownOne").runAnimation();
				else if(random == 1) playerAnimations.get("playerPunchDownTwo").runAnimation();
				else playerAnimations.get("playerKickDown").runAnimation();
			}else{
				if(random == 0) playerAnimations.get("playerPunchUpOne").runAnimation();
				else if(random == 1) playerAnimations.get("playerPunchUpTwo").runAnimation();
				else playerAnimations.get("playerKickUp").runAnimation();
			}	
		}
	}

	
	public void render(Graphics g) {
		
		hud(g);
		
		if(playerState == PlayerState.Idle_Walking){
			if(velX != 0){
				if(direction == 1){
					playerAnimations.get("playerWalkRight").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}else if (direction == 2){
					playerAnimations.get("playerWalkLeft").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}
			}else if(velY != 0){
				if(direction == 3){
					playerAnimations.get("playerWalkDown").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}else if(direction == 4){
					playerAnimations.get("playerWalkUp").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				}
			}else{
				if(direction == 1) playerAnimations.get("playerStandRight").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(direction == 2) playerAnimations.get("playerStandLeft").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(direction == 3) playerAnimations.get("playerStandDown").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(direction == 4) g.drawImage(playerActions[3][0], (int)x, (int)y+10, (int)width, (int)height, null);
			}
		}else if(playerState == PlayerState.Action){
			if(direction == 1) {
				if(random == 0) playerAnimations.get("playerPunchRightOne").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(random == 1) playerAnimations.get("playerPunchRightTwo").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else playerAnimations.get("playerKickRight").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
			}else if(direction == 2){
				if(random == 0) playerAnimations.get("playerPunchLeftOne").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(random == 1) playerAnimations.get("playerPunchLeftTwo").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else playerAnimations.get("playerKickLeft").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
			}else if(direction == 3) {
				if(random == 0) playerAnimations.get("playerPunchDownOne").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(random == 1) playerAnimations.get("playerPunchDownTwo").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else playerAnimations.get("playerKickDown").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
			}else{
				if(random == 0) playerAnimations.get("playerPunchUpOne").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else if(random == 1) playerAnimations.get("playerPunchUpTwo").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
				else playerAnimations.get("playerKickUp").drawAnimation(g, (int)x, (int)y+10, (int)width, (int)height);
			}
		}
	}
	
	private void collision(List<GameObject> object){
		
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			
			if(tempObject.getId() == ObjectId.Block){
				if(getBoundsTop().intersects(tempObject.getBounds())){
					y = tempObject.getY() + 36;
				}
				
				if(getBounds().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
				}
				
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX() - width + 5;
				}
				
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX() + 31;
				}
			}
		}
	}
	
	public void hud(Graphics g){
		g.setColor(Color.RED);
		g.fillRect((int)x+12, (int)y-3, 30, 6);
		
		g.setColor(Color.GREEN);
		g.fillRect((int)x+12, (int)y-3, (int)playerHealth, 6);
		
		g.setColor(Color.WHITE);
		g.drawRect((int)x+12, (int)y-3, 30, 6);
	
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x+(width/2)-(width/4)), (int)(y+(height/4)), 28, 50);
	}
	
	public Rectangle getBoundsTop(){
		return new Rectangle((int)(x+(width/2)-((width/2)/2)), (int)y, (int)width/2, (int)height/2);
	}
	
	public Rectangle getBoundsRight(){
		return new Rectangle((int)(x+width-5), (int)y+5, (int)5, (int)height-10);
	}
	
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x, (int)y+5, (int)5, (int)height-10);
	}
	
	public int getDirection(){
		return direction;
	}
}
