import java.awt.Graphics;
import java.awt.Point;

public interface BaseSpriteI {

	// This is a function that sets the X,Y cords for any object

	public void placeSprite(int x, int y);

	// moveTo sets a location for an object to walk to at its set speed
	void moveTo(int x, int y);

	// move will allow the object to move to the location it has been set to go to
	boolean move();

	// sets visibility to true or false
	void setvisible(boolean visability);

	// paint paints in the object with the graphics object (im not sure of this as
	// of now)
	boolean paint(Graphics g);

	// gets the x position of Playable character
	Point getPosition();

	boolean isColliding(BaseSprite testedSprite); // returns true if objects collide

	//makes it so you get the gold from playable character
	int getGold();
	
	//lets us set the gold
	void setGold(int g);
	
	int incrementGold();
}
