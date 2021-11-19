import java.awt.Dimension;
/**
 * @author Aidan Scott
 * this class exists to allow classification of sprites that have internal desition checking if they are colliding with another sprite
 */
public abstract class CollisionSprite extends BaseSprite{
	/**
	 * @param int x
	 * @param String fileName
	 * @param Dimension panlDementions
	 */
	protected CollisionSprite(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param CollisionController colControl 
	 */
	abstract void checkCollision(CollisionController colControl);
	
	
}
