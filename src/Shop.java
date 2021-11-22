import java.awt.Dimension;
/**
 * @author Aidan Scott
 */
public class Shop extends BaseSprite{
	private ShopType shopType;
	/**
	 * @param x is the x position, top left, of where the sprite will be spawned
	 * @param fileName is the filename of the image that will be used for the sprite
	 * @param panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 * @param shop sets the type of shop
	 */
	protected Shop(int x, String fileName, Dimension panelDimensions, ShopType shop) {
		super(x, fileName, panelDimensions);
		this.currentPosition.y = this.currentPosition.y - 50;
		shopType = shop;
	}
	/**
	 * @return shopType
	 */
	public ShopType getShopType() {
		return shopType;
		
	}
}
