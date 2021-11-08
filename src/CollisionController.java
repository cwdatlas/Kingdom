import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionController {
	List<BaseSprite> objectList;
	
	public CollisionController(List<BaseSprite> list) {
		objectList = list;
	}
	
	public List<BaseSprite> checkCollition(Rectangle hitBox){
		List<BaseSprite> returnableObjects = new ArrayList<>();
		
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
		return objectList.add(addObject);
		
	}
	
}
