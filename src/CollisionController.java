import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionController {
	ArrayList<BaseSprite> objectList;
	ArrayList<BaseSprite> returnableObjects;
	
	public CollisionController(ArrayList<BaseSprite> list) {
		objectList = list;
		returnableObjects = new ArrayList<BaseSprite>();
	}
	
	public ArrayList<BaseSprite> checkCollition(Rectangle hitBox){
		for(int i = 0; i<objectList.size();  i++) {
			if(hitBox.intersects(objectList.get(i).getHitBox()));
			returnableObjects.add(objectList.get(i));
		}
//		System.out.println("returing collided objects");
		return returnableObjects;
	}
	
	
	public boolean deleteObject(BaseSprite delObject) {
		
		return false;
	}
	public boolean addObject(BaseSprite addObject) {
		return false;
		
	}
	
}
