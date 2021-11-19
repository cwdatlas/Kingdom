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
 * BaseSprite is the parent of all sprite classes
 * it is used to have an easy system to implement images, hitboxes, movement, and painting across all sprites
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
	 * 
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 */
	protected BaseSprite(int x, String fileName, Dimension panelDimensions) {
		int y = (int)(panelDimensions.height*.74);
		target = new Point(x, y);
		currentPosition = new Point(x, y);
		dimensions = panelDimensions;
		this.loadImage(fileName);
		hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	/**
	 * placeSprite sets sprite location to x and y values it is given
	 *@param int x is the x location
	 *@param int y is the y location
	 */
	@Override
	public void placeSprite(int x, int y) {
		currentPosition.setLocation(new Point(x, y));	
	}
	/**
	 * moveTo sets the target location to where the sprite will move to over time
	 *@param int x is the x target location
	 *@param int y is the y target location
	 *@see move() to understand how the location of the sprite changes with each call of the function
	 */
	@Override
	public void moveTo(int x, int y) {
		target.setLocation(new Point(x, y));
		
	}
	/**
	 * move() moves the sprite towards its target direction
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
	 * setvisible() toggles the spites visibility
	 * being invisible stop the paint function from painting the sprite
	 */
	@Override
	public void setvisible(boolean visability) {
			visible = visability;
	}
	/**
	 * moveTo sets the target location to where the sprite will move to over time
	 * @param Graphics g needs to be the graphics components gotten from a paint function
	 * @return boolean visible
	 */
	@Override
	public boolean paint(Graphics g) {
		if(visible) {
		g.drawImage(img, currentPosition.x, currentPosition.y, null);
		}
		return visible;
	}
	/**
	 * loadImage sets bufferedImage to found image
	 *@param String fileName image name
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
	 * getPosition returns the cords of the Sprite
	 * @return point curentPosition which is the position of the sprite
	 */
	@Override
	public Point getPosition() {
		return currentPosition;
	}
	/**
	 * getHitBox returns the standardized hitbox that all baseSprites have
	 * @return rectangle hitbox
	 */
	public Rectangle getHitBox() {
		return hitbox;
	}
	/**
	 * setBlocked will set a stop a sprite from moving through another sprite like a wall 
	 * this function is specifically built to work will walls
	 *@param boolean blocked true if a sprite is blocked by another
	 *@see wall colliton to see an example of this being used
	 */
	//the setter for blocking and the direction that the sprite is blocked in
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
	 * @return int gold 
	 */
	@Override
	public int getGold() {
		return gold;
	}
	/**
	 * @param int g sets gold to the quantity of gold passed into the function
	 */
	@Override
	public void setGold(int g) {
		gold = g;	
	}
	/**
	 * incrementGold adds 1 to the supply of gold the sprite has
	 * @return int gold 
	 */
	@Override
	public int incrementGold() {
		gold++;
		return gold;
	}
	/**
	 * minusGold decreases the supply of gold the sprite has by 1
	 * @return int gold
	 */
	public int minusGold() {
		gold--;
		return gold;
		
	}
}
