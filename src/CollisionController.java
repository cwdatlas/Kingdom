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
			if(hitBox.intersects(objectList.get(i).getHitBox()) && hitBox != objectList.get(i).getHitBox() && objectList.get(i).visible) {
				returnableObjects.add(objectList.get(i));
			}
		}
		return returnableObjects;
		

	}
	
	public boolean deleteObject(BaseSprite delObject) {
		
		return objectList.remove(delObject);
	}
	public boolean addObject(BaseSprite addObject) {
		System.out.println("added "+ addObject.getClass());
		return objectList.add(addObject);
		
	}
	
}
