package window;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import framework.GameObject;
import framework.ObjectId;

/**
 * This is the handler class which holds all of the game objects that are used in the game.
 * Such as characters and map tiles.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Handler {

	public List<GameObject> object = new ArrayList<GameObject>();
	private Map<GameObject, Float> spritePositions = new HashMap<GameObject, Float>();
	
	private GameObject tempObject, playerObject;
	
	public void tick(){
		for(int i = 0; i < object.size(); i++){
			tempObject = object.get(i);
			
			tempObject.tick(object);
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < object.size(); i++){
			tempObject = object.get(i);
			
			tempObject.render(g);
		}
		
		for(int i = 0; i < object.size(); i++){
			if(object.get(i).getId() == ObjectId.Player){
				playerObject = object.get(i);
				spritePositions.put(playerObject, playerObject.getY());
			}
			
			if(object.get(i).getId() == ObjectId.Baddie){
				spritePositions.put(object.get(i), object.get(i).getY());
			}
		}

		spritePositions = sortMap(spritePositions);
		
		for(Map.Entry<GameObject, Float> entry : spritePositions.entrySet()){
			entry.getKey().render(g);
		}
		
		spritePositions.clear();
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	
	public boolean contains(ObjectId id){
		for(int i = 0; i < object.size(); i++){
			if(object.get(i).getId() == id){
				return true;
			}
		}
		
		return false;
	}
	
	private static Map<GameObject, Float> sortMap(Map<GameObject, Float> map){
		List<Map.Entry<GameObject, Float>> list = new LinkedList<Map.Entry<GameObject, Float>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<GameObject, Float>>(){

			@Override
			public int compare(Entry<GameObject, Float> o1,
					Entry<GameObject, Float> o2) { 
				return o1.getValue().compareTo(o2.getValue());
			}
			
		});
		
		Map<GameObject, Float> sortedMap = new LinkedHashMap<GameObject, Float>();
		
		for(Map.Entry<GameObject, Float> entry : list){
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortedMap;
	}
}
