package window;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import framework.ObjectId;
import framework.SpriteSheet;

/**
 * This is the TileMapLoader class which is used to create the map.
 * It creates the map by using a JSON parser to read a JSON file that is made from a program called "Tiled"
 * This program is used to create the layered map. The map is then exported as a JSON file and this class reads the file.
 * Thus, creating the needed map.
 * 
 * @author Aleksandar Spasic
 *
 */
public class TileMapLoader {
	public Tile[][] tileLayer1 = new Tile[30][30], tileLayer2 = new Tile[30][30], tileLayer3 = new Tile[30][30], tileLayer4 = new Tile[30][30], collisionLayer = new Tile[30][30];
	private Handler handler;
	private BufferedImageLoader loader = new BufferedImageLoader();
	private SpriteSheet spriteSheet, collisionSheet;
	
	public TileMapLoader(String filePath, Handler handler) throws FileNotFoundException, IOException, ParseException{
		this.handler = handler;
		loadLevel(filePath);
	}
	
	public void loadLevel(String filePath) throws FileNotFoundException, IOException, ParseException{
		int tileLocationX = 0, tileLocationY = 0;
		
		Object obj = new JSONParser().parse(new FileReader(filePath));
		BufferedImage tilesetImage = loader.loadImage("res/PokemonTileSet.png");
		BufferedImage collisionTileset = loader.loadImage("res/CollisionLayer.png");
		spriteSheet = new SpriteSheet(tilesetImage);
		collisionSheet = new SpriteSheet(collisionTileset);
		JSONObject jo = (JSONObject) obj;
		
		int width = Integer.parseInt(String.valueOf(jo.get("width")));
		int height = Integer.parseInt(String.valueOf(jo.get("height")));
		
		JSONArray layers = (JSONArray) jo.get("layers");
		JSONObject index = (JSONObject) layers.get(0);
		JSONArray layerData = (JSONArray) index.get("data");
		
		int count = 0;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int tileId = Integer.parseInt(String.valueOf(layerData.get(count)));
				if(tileId != 0){
					tileLocationX = tileId % 8;
					if(tileLocationX == 0){
						tileLocationX = 8;
						tileLocationY = tileId / 8;
					}else{
						tileLocationY = (tileId + (8-tileLocationX)) / 8;
					}
					tileLayer1[i][j] = new Tile(j*64, i*64, ObjectId.Tile, spriteSheet.grabImage(tileLocationX, tileLocationY, 32, 32), handler);
				}else{
					tileLayer1[i][j] = null;
				}
				count++;
			}
		}
		
		index = (JSONObject) layers.get(1);
		layerData = (JSONArray) index.get("data");
		
		count = 0;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int tileId = Integer.parseInt(String.valueOf(layerData.get(count)));
				if(tileId != 0){
					tileLocationX = tileId % 8;
					if(tileLocationX == 0){
						tileLocationX = 8;
						tileLocationY = tileId / 8;
					}else{
						tileLocationY = (tileId + (8-tileLocationX)) / 8;
					}
					tileLayer2[i][j] = new Tile(j*64, i*64, ObjectId.Tile, spriteSheet.grabImage(tileLocationX, tileLocationY, 32, 32), handler);
				}else{
					tileLayer2[i][j] = null;
				}
				count++;
			}
		}
		
		index = (JSONObject) layers.get(3);
		layerData = (JSONArray) index.get("data");
		
		count = 0;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int tileId = Integer.parseInt(String.valueOf(layerData.get(count)));
				if(tileId != 0){
					tileLocationX = tileId % 8;
					if(tileLocationX == 0){
						tileLocationX = 8;
						tileLocationY = tileId / 8;
					}else{
						tileLocationY = (tileId + (8-tileLocationX)) / 8;
					}
					tileLayer3[i][j] = new Tile(j*64, i*64, ObjectId.Tile, spriteSheet.grabImage(tileLocationX, tileLocationY, 32, 32), handler);
				}else{
					tileLayer3[i][j] = null;
				}				
				count++;
			}
		}
		
		index = (JSONObject) layers.get(4);
		layerData = (JSONArray) index.get("data");
		
		count = 0;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int tileId = Integer.parseInt(String.valueOf(layerData.get(count)));
				if(tileId != 0){
					tileLocationX = tileId % 8;
					if(tileLocationX == 0){
						tileLocationX = 8;
						tileLocationY = tileId / 8;
					}else{
						tileLocationY = (tileId + (8-tileLocationX)) / 8;
					}
					tileLayer4[i][j] = new Tile(j*64, i*64, ObjectId.Tile, spriteSheet.grabImage(tileLocationX, tileLocationY, 32, 32), handler);
				}else{
					tileLayer4[i][j] = null;
				}
				count++;
			}
		}
		
		index = (JSONObject) layers.get(5);
		layerData = (JSONArray) index.get("data");
		count = 0;
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int tileId = Integer.parseInt(String.valueOf(layerData.get(count)));
				if(tileId == 5209){
					collisionLayer[i][j] = new Tile(j*64, i*64, ObjectId.Tile, collisionSheet.grabImage(1, 1, 32, 32), handler);
				}else if(tileId == 5210){
					collisionLayer[i][j] = new Tile(j*64, i*64, ObjectId.Tile, collisionSheet.grabImage(2, 1, 32, 32), handler);
				}else{
					collisionLayer[i][j] = null;
				}
				count++;
//				System.out.println("X-Coordinate: " + i + " Y-Coordinate: " + j + " Tile Id: " + tileId + " TileLocationX: " + tileLocationX + " TileLocationY: " + tileLocationY);
			}
		}
	}
}
