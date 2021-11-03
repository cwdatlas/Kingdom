//programmed by Aidan and Adrien of Carroll college 
public interface PlayableCharacterI {

	//moveLeft sets the direction of movement to left at a constant rate 
	boolean moveLeft();
	
	//moveRight sets the direction of movement to right at a constant rate
	boolean moveRight();
	
	//stopMoving stops the object from moving
	boolean stopMoving();
	
	//useMoney sets use of money to true or false, which then has the character use money when in rage of an
	//object that allows money to be given
	boolean useMoney(boolean use);
	
}
