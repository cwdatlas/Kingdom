import java.awt.Dimension;
/**
 * @author Aidan Scott
 * class is simply a placeholder for the coin image and its hitbox
 */	
public class DroppedCoin extends BaseSprite{
	/**
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 * @see BaseSprite for more information
	 */
	protected DroppedCoin(int x, String fileName, Dimension panelDimensions) {
		super(x, fileName, panelDimensions);
		// TODO Auto-generated constructor stub
	}


	
}
