package window;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import objects.Player;
import framework.KeyInput;
import framework.ObjectId;
import framework.Spawn;

/**
 * This is where the main logic of the game lies.
 * The whole game runs off the run method, which is a continuous loop.
 * The loop updates the game every second to show movement animations and keep track of sprite and map position.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	private boolean running = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private Spawn spawn;
	private Tile[][] tileLayer1, tileLayer2, tileLayer3, tileLayer4, collisionLayer;
	private Menu menu;
	private HelpScreen helpScreen;
	
	public static int WIDTH, HEIGHT;
	
	public enum STATE{
		Menu,
		Help,
		Game;
	}
	
	public static STATE gameState = STATE.Menu;
	
	private void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		handler = new Handler();
		menu = new Menu();
		helpScreen = new HelpScreen();
		camera = new Camera(0, 0);
		spawn = new Spawn(handler);
		
		try {
			TileMapLoader tileMapLoader = new TileMapLoader("res/TestMap2..json", handler);
			tileLayer1 = tileMapLoader.tileLayer1;
			tileLayer2 = tileMapLoader.tileLayer2;
			tileLayer3 = tileMapLoader.tileLayer3;
			tileLayer4 = tileMapLoader.tileLayer4;
			collisionLayer = tileMapLoader.collisionLayer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		handler.addObject(new Player(900, 300, ObjectId.Player));
	}
	
	public synchronized void start(){
		if(running){
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){}
	
	public void run() {
		init();
		this.requestFocus();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running){
			long now = System.nanoTime();
			
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}

			render();	
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick(){
		handler.tick();
		
		if(gameState == STATE.Menu){
			menu.tick();
		}else if(gameState == STATE.Help){
			
		}else{
			for(int i = 0; i < handler.object.size(); i++){
				if(handler.object.get(i).getId() == ObjectId.Player){
					camera.tick(handler.object.get(i));
				}
			}
			
			for(int i = 0; i < handler.object.size(); i++){
				if(handler.object.get(i).getId() == ObjectId.Player){
					for(int j = 0; j < 30; j++){
						for(int k = 0; k < 30; k++){
							if(collisionLayer[j][k] != null) collisionLayer[j][k].collision();
						}
					}
				}
			}
			
			spawn.tick();
		}
	}
	
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		if(gameState == STATE.Menu){
			menu.render(g);
		}else if(gameState == STATE.Help){
			helpScreen.render(g);
		}else{
			
			Color backgroundColor = new Color(182, 226, 160);
			
			g.setColor(backgroundColor);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			if(camera.getX() < 1.0 && camera.getX() > -1094.0 && camera.getY() < -3.0 && camera.getY() > -1200.0){
				
			}
			
			g2d.translate(camera.getX(), camera.getY());	//Beginning of Camera
			
			for(int i = 0; i < 30; i++){
				for(int j = 0; j < 30; j++){
					if(tileLayer1[i][j] != null) tileLayer1[i][j].render(g);
				}
			}
			
			for(int i = 0; i < 30; i++){
				for(int j = 0; j < 30; j++){
					if(tileLayer2[i][j] != null) tileLayer2[i][j].render(g);
				}
			}
			
			for(int i = 0; i < 30; i++){
				for(int j = 0; j < 30; j++){
					if(tileLayer3[i][j] != null) tileLayer3[i][j].render(g);
				}
			}
			
			handler.render(g);
			
			for(int i = 0; i < 30; i++){
				for(int j = 0; j < 30; j++){
					if(tileLayer4[i][j] != null) tileLayer4[i][j].render(g);
				}
			}
			
			g2d.translate(-camera.getX(), -camera.getY());	//End of Camera
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		new Window(800, 600, "Senior Capstone", new Game());
	}
}
