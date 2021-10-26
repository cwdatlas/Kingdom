
public interface DefenderI {

	void setDefending(); //sets the defenders moveTo point to be in their defensive area
	
	void setRoaming(); //sets the defenders actions to be in the roaming status
	
	void setMode();
	
	void checkCollision(CollisionController colControl);
}
