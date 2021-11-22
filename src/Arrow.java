import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author Aidan Scott
 *@see BaseSprite for more information on how Arrow runs
 */
public class Arrow extends CollisionSprite {
	boolean right;
	boolean delete = false;

	Random random = new Random();
	
	/**
	 * the constructor will set the y position of the arrow, and build its hitbox for the dimensions of the img
	 * 
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 * @param boolean goingRight describes if the arrow is moving to the right or not(left)
	 */
	protected Arrow(int x, String fileName, Dimension panelDementions, boolean goingRight) {
		super(x, fileName, panelDementions);
		dimensions = panelDementions;
		int y = currentPosition.y + 30;
		currentPosition.y = y;
		hitbox = new Rectangle(x, currentPosition.y, img.getWidth(), img.getHeight());
		right = goingRight;
	}

	/**
	 * the move() function overrides the move() function in baseSprite allowing it to increase its speed from 1px to 3px
	 * 
	 *@see move() in baseSprite to see the overridden function
	 */
	@Override
	public boolean move() {
		boolean check = false;
		try {
			if (right) {
				currentPosition.setLocation(new Point(currentPosition.x + 3, currentPosition.y));
				hitbox.x = currentPosition.x;
			} else {
				currentPosition.setLocation(new Point(currentPosition.x - 3, currentPosition.y));
				hitbox.x = currentPosition.x;

			}
			if (this.currentPosition.x < 0 || this.currentPosition.x > dimensions.width) {
				delete = true;
			}
		
		} finally {

		}
		return check;

	}

	/**
	 * checkCollision() is the class by class system for deciding what happens when a collision is detected by colControl
	 *
	 *@param colControl is the centralized collision detection system for Kingdom
	 *@see CollisionController to learn more about its .checkCollition(rectangle hitbox) function
	 */
	@Override
	public void checkCollision(CollisionController colControl) {
		List<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for (int i = 0; i < collidingSprites.size(); i++) {
			if (collidingSprites.get(i) instanceof Enemy) {
				Enemy effected = (Enemy) collidingSprites.get(i);
				effected.setRetreat();
				colControl.addObject(new DroppedCoin(random.nextInt(20)+currentPosition.x, "coin.png", dimensions));
				colControl.deleteObject(effected);
				delete = true;
			}

		}
		if (delete) {
			colControl.deleteObject(this);
		}
	}

}
