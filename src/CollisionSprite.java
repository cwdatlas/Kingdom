import java.awt.Dimension;
/**
 * @author Aidan Scott
 */
public abstract class CollisionSprite extends BaseSprite{
	/**
	 * @param x
	 * @param fileName
	 * @param panlDementions
	 */
	protected CollisionSprite(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param colControl 
	 */
	abstract void checkCollision(CollisionController colControl);
	
	
}
