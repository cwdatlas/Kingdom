import java.util.List;
import java.awt.Rectangle;
import java.util.ArrayList;
/**
 * @author Aidan Scott
 * CollisionController is a centralized collision processing system
 * other centralized methods that closely work with the BaseSprite list also are located here
 */ class CollisionController {
	private List<BaseSprite> objectList;
	public int enemiesKilled = 0;
	public int defendersKilled = 0;
	public int defendersSpawned = 0;
	/**
	 * @param List<BaseSprite> list is the list of sprites that will be checked against when checking for collitions
	 * @see checkCollition()
	 */
	public CollisionController(List<BaseSprite> list) {
		objectList = list;
	}
	/**
	 * checkCollition checks all objects in objectList to see what is colliding with hitbox
	 * @param Rectangle hitbox 
	 * @return List<BaseSprite> returnableObjects is the list of objects that are colliding with the given hitbox
	 */
	public List<BaseSprite> checkCollition(Rectangle hitBox){
		List<BaseSprite> returnableObjects = new ArrayList<>();
		
		for(int i = 0; i<objectList.size();  i++) {
			if(hitBox.intersects(objectList.get(i).getHitBox()) && hitBox != objectList.get(i).getHitBox() && objectList.get(i).visible) {
				returnableObjects.add(objectList.get(i));
			}
		}
		return returnableObjects;
	}
	/**
	 * deleteObject deletes an object within the given BaseSprite list
	 * @param BaseSprite delObject is the object that will be deleted out of the list
	 * @return boolean true if object was deleted
	 * @see Arrow to see example of this being used
	 */
	public boolean deleteObject(BaseSprite delObject) {
		if(delObject instanceof Enemy)
			enemiesKilled++;
		else if(delObject instanceof Defender)
			defendersKilled++;
		return objectList.remove(delObject);
	}
	/**
	 * addObject will add an object to the BaseSprite list
	 * @return true if object was added
	 */
	public boolean addObject(BaseSprite addObject) {
		if(addObject instanceof Defender)
			defendersSpawned++;
		return objectList.add(addObject);
		
	}
	
}
