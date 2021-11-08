import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionController {
	private List<BaseSprite> objectList;
	public int enemiesKilled = 0;
	public int defendersKilled = 0;
	
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
		if(delObject instanceof Enemy)
			enemiesKilled++;
		else if(delObject instanceof Enemy)
			defendersKilled++;
		return objectList.remove(delObject);
	}
	public boolean addObject(BaseSprite addObject) {
		return objectList.add(addObject);
		
	}
	
}
