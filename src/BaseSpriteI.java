import java.awt.Graphics;

public interface BaseSpriteI {

	//This is a function that sets the X,Y cords for any object
	public boolean place (int x, int y);
	
	//moveTo sets a location for an object to walk to at its set speed
	public boolean moveTo (int x, int y);
	
	//move will allow the object to move to the location it has been set to go to
	public boolean move();
	
	//sets visibility to true or false
	public boolean remove(boolean visability);
	
	//paint paints in the object with the graphics object (im not sure of this as of now)
	public boolean paint(Graphics g);
	
	
}

