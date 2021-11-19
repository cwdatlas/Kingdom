import java.awt.Dimension;
/**
 * @author Aidan Scott
 * Shop allows a diverse set of shops to be created depending on the shop stated in the constructor
 */
public class Shop extends BaseSprite{
	private ShopType shopType;
	/**
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 * @param ShopType shop sets the type of shop
	 */
	protected Shop(int x, String fileName, Dimension panelDimensions, ShopType shop) {
		super(x, fileName, panelDimensions);
		shopType = shop;
	}
	/**
	 * @return shopType
	 */
	public ShopType getShopType() {
		return shopType;
		
	}
}
