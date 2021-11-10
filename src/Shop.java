import java.awt.Dimension;

public class Shop extends BaseSprite{
	private ShopType shopType;

	protected Shop(int x, String fileName, Dimension panelDimensions, ShopType shop) {
		super(x, fileName, panelDimensions);
		shopType = shop;
	}
	
	public ShopType getShopType() {
		return shopType;
		
	}
}
