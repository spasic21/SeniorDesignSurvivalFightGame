package framework;

import objects.Baddie;
import objects.Player;
import window.Handler;

/**
 * This class is used to spawn in all of the aliens after they get defeated.
 * 
 * @author Aleksandar Spasic
 *
 */
public class Spawn {

	private Handler handler;
	
	public Spawn(Handler handler){
		this.handler = handler;
	}
	
	public void tick(){
		
		if(!handler.contains(ObjectId.Baddie)){
			switch(Player.bodyCount){
			case 0:
				handler.addObject(new Baddie(900, 600, ObjectId.Baddie, handler));
				break;
			case 1:
				handler.addObject(new Baddie(900, 600, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 700, ObjectId.Baddie, handler));
				break;
			case 3:
				handler.addObject(new Baddie(900, 600, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 700, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 800, ObjectId.Baddie, handler));
				break;
			case 6:
				handler.addObject(new Baddie(900, 600, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 800, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1000, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1200, ObjectId.Baddie, handler));
				break;
			case 10:
				handler.addObject(new Baddie(900, 600, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 800, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1000, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1200, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1500, ObjectId.Baddie, handler));
				break;
			case 15:
				handler.addObject(new Baddie(900, 600, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 800, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1000, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1200, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1500, ObjectId.Baddie, handler));
				handler.addObject(new Baddie(900, 1800, ObjectId.Baddie, handler));
				break;
			}
		}
	}
}
