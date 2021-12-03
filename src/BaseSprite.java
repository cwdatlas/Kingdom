import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/**
 * @author Aidan Scott & Adrien
 */
public abstract class BaseSprite implements BaseSpriteI {
	protected BufferedImage img;
	protected Point target;
	protected Point currentPosition;
	protected Dimension dimensions;
	protected Rectangle hitbox;
	protected boolean blockedRight = false;
	protected boolean blockedLeft = false;
	protected boolean visible = true;
	protected int gold;
	/**
	 * This constructor builds the Sprites hitbox, sets y level, loads image and sets general variables
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 */
	protected BaseSprite(int x, String fileName, Dimension panelDimensions) {
		this.loadImage(fileName);
		int y = (int)(panelDimensions.height*.822) - img.getHeight();
		target = new Point(x, y);
		currentPosition = new Point(x, y);
		dimensions = panelDimensions;
		hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	/**
	 * placeSprite() sets sprite location to the x and y values
	 *@param x is the x location
	 *@param y is the y location
	 */
	@Override
	public void placeSprite(int x, int y) {
		currentPosition.setLocation(new Point(x, y));	
	}
	/**
	 * moveTo() sets the target location of the sprite
	 *@param x is the x target location
	 *@param y is the y target location
	 *@see move() to understand how the location of the sprite changes with each call of the function
	 */
	@Override
	public void moveTo(int x, int y) {
		target.setLocation(new Point(x, y));
		
	}
	/**
	 * move() moves the sprite towards its target location
	 *the location of the sprite will be changed by 1px per call of this function
	 */
	@Override
	public boolean move() {
		boolean check = false;
		try {
			if(target.x > currentPosition.x && !blockedRight ) {
				currentPosition.setLocation(new Point(currentPosition.x + 1, currentPosition.y));
				hitbox.x = currentPosition.x;
			}
			else if(target.x < currentPosition.x && !blockedLeft) {
				currentPosition.setLocation(new Point(currentPosition.x - 1, currentPosition.y));
				hitbox.x = currentPosition.x;
			}
		}finally{
		}
		
		return check;
	}
	/**
	 * setVisible() toggles the spites visibility
	 * being invisible stops the paint() from painting the sprite
	 */
	@Override
	public void setVisible(boolean visability) {
			visible = visability;
	}
	/**
	 * paint() displays the sprite on KingdomController
	 * @param g
	 */
	@Override
	public boolean paint(Graphics g) {
		if(visible) {
		g.drawImage(img, currentPosition.x, currentPosition.y, null);
		}
		return visible;
	}
	/**
	 * loadImage() takes fileName and gets the file from /images/
	 *@param fileName image name
	 */
	public void loadImage(String fileName) {
		if (fileName != null) {
			try {
				img = ImageIO.read(this.getClass().getResource("/images/" + fileName));
			} catch (IOException e) {
				System.out.println("Image not found.");
			}
		}
	}
	/**
	 * getPosition() returns the cords of the Sprite
	 * @return curentPosition
	 */
	@Override
	public Point getPosition() {
		return currentPosition;
	}
	/**
	 * getHitBox() returns the standardized hitbox that all baseSprites have
	 * @return hitbox
	 */
	public Rectangle getHitBox() {
		return hitbox;
	}
	/**
	 * setBlocked() will set a stop a sprite from moving through wall sprites
	 *@param blocked true if a sprite is blocked by a wall
	 *@see Wall to see an example of this being used
	 */
	public void setBlocked(boolean blocking) {
		if(target.x > currentPosition.x && blocking) {
			blockedRight = true;
		}
		else if(blocking) {
			blockedLeft = true;
		}
		else {
			blockedLeft = false;
			blockedRight = false;
		}
	}
	/**
	 * @return gold 
	 */
	@Override
	public int getGold() {
		return gold;
	}
	/**
	 * @param g sets gold to the quantity of gold passed into the function
	 */
	@Override
	public void setGold(int g) {
		gold = g;	
	}
	/**
	 * incrementGold() adds 1 to the supply of gold the sprite has
	 * @return gold 
	 */
	@Override
	public int incrementGold() {
		gold++;
		return gold;
	}
	/**
	 * minusGold() decreases the supply of gold the sprite has by 1
	 * @return gold
	 */
	public int minusGold() {
		gold--;
		return gold;
		
	}
}
