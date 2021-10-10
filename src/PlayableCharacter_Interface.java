//programmed by Aidan and Adrien of Carroll college 
public interface PlayableCharacter_Interface {

	//moveLeft sets the direction of movement to left at a constant rate 
	public boolean moveLeft();
	
	//moveRight sets the direction of movement to right at a constant rate
	public boolean moveRight();
	
	//stopMoving stops the object from moving
	public boolean stopMoving();
	
	//useMoney sets use of money to true or false, which then has the character use money when in rage of an
	//object that allows money to be given
	public boolean useMoney(boolean use);
}
