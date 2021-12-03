
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * @author Aidan Scott & Adrien
 * @see kingdomController to change initialization
 */
public class PlayableCharacter extends CollisionSprite implements PlayableCharacterI {
	// set your varables here, make sure as many as possible are private
	private Random random = new Random();
	private boolean downPress = false;
	private boolean playerShooting = false;
	private boolean shootingRight = true;
	private int goldUseTimer = 0;
	private int goldWarningTimer = 0;
	private int arrowDrawTimer = 0;
	private int arrowWarningTimer = 0;
	private int arrows = 0;
	/**
	 * @param x is the x position, top left, of where the sprite will be spawned
	 * @param Gold is the quantity of gold player has
	 * @param arrowsGiven quantity of arrows given to the player
	 * @param fileName is the filename of the image that will be used for the sprite
	 * @param panelDementions are the dimensions for the panel being used
	 *                  so the arrow can be placed at the correct y value
	 */
	PlayableCharacter(int x, int Gold, int arrowsGiven, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		arrows = arrowsGiven;
		this.gold = Gold;
	}
	/**
	 * moveLeft() moves the player left
	 */
	@Override
	public boolean moveLeft() {
		currentPosition.setLocation(new Point((int) currentPosition.getX() - 2, currentPosition.y));
		hitbox.setLocation(new Point((int) currentPosition.getX() - 2, currentPosition.y));
		return true;
	}
	/**
	 * moveRight() moves the player right
	 */
	@Override
	public boolean moveRight() {
		currentPosition.setLocation(new Point((int) currentPosition.getX() + 2, currentPosition.y));
		hitbox.setLocation(new Point((int) currentPosition.getX() + 2, currentPosition.y));
		return true;
	}
	/**
	 *downPress() should be called when the user presses down
	 *on the numpad, which then press is set to true
	 */
	@Override
	public void downPress(boolean press) {
		downPress = press;
	}
	/**
	 *playerShoot() is called when the player needs to shoot an arrow 
	 *@param shooting
	 *@param position
	 */
	public void playerShoot(boolean shooting, boolean direction) {
		playerShooting = shooting;
		shootingRight = direction;
	}
	/**
	 * @return arrows
	 */
	public int getArrows() {
		return arrows;
	}
	/**
	 * checkCollision() asks colControl if player's hitbox is colliding with any other hitbox
	 * checkCollision calculates what happens when collision events occur
	 * @param colControl
	 * @see CollisionController to see how collision works
	 */
	public void checkCollision(CollisionController colControl) {
		List<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for (int i = 0; i < collidingSprites.size(); i++) {
			if (collidingSprites.get(i) instanceof DroppedCoin) {
				DroppedCoin coin = (DroppedCoin) collidingSprites.get(i);
				colControl.deleteObject(coin);
				this.incrementGold();
			} else if (collidingSprites.get(i) instanceof Enemy) {
				this.setVisible(false);
			} else if (collidingSprites.get(i) instanceof Wall) {
				if (downPress && this.getGold() >= 3 && goldUseTimer == 0) {
					Wall wall = (Wall) collidingSprites.get(i);
					if (wall.HP < 50) {
						wall.rebuildWall();
						this.setGold(this.getGold() - 3);
						resetGoldTimer();
					}
				}else if(downPress && goldUseTimer == 0) {
					goldWarningTimer = 700;
				}
			} else if (collidingSprites.get(i) instanceof Shop && goldUseTimer == 0) {
				Shop shop = (Shop) collidingSprites.get(i);
				if (shop.getShopType() == ShopType.arrowType) {
					if (downPress && this.getGold() >= 2) {
						arrows++;
						this.setGold(this.getGold() - 2);
						resetGoldTimer();
					}else if(downPress) {
						goldWarningTimer = 700;
					}

				} else if (shop.getShopType() == ShopType.defenderType) {
					if (downPress && this.getGold() >= 3) {
						colControl.addObject(new Defender(800, "defenderSprite.png", dimensions));
						this.setGold(this.getGold() - 3);
						resetGoldTimer();
					}else if(downPress){
						goldWarningTimer = 700;
					}
				}
			}
		}
		// press down to drop gold
//		if (downPress && gold > 0 && goldUseTimer == 0) {
//			this.gold--;
//			colControl.addObject(new DroppedCoin(currentPosition.x, "coin.png", dimensions));
//			resetGoldTimer();
//			droppedGoldTimer = 200;
//		}
		// player shoots arrow
		if (playerShooting && arrows > 0 && arrowDrawTimer == 0) {
			this.arrows--;
			Arrow arrow = new Arrow(currentPosition.x, "arrowSprite.png", dimensions, shootingRight);
			arrow.moveTo(target.x, 500);
			colControl.addObject(arrow);
			resetArrowDrawTimer();
		}else if(arrowDrawTimer == 0 && playerShooting)
			arrowWarningTimer = 700;

		// incrementing timers down
		if (goldUseTimer > 0)
			goldUseTimer--;
		if (goldWarningTimer > 0)
			goldWarningTimer--;
		if (arrowDrawTimer > 0)
			arrowDrawTimer--;
		if (arrowWarningTimer > 0)
			arrowWarningTimer--;
	}
	/**
	 * paint() is overriden to allow a red square to be placed around the player
	 * @param g
	 * @see Defender to see how this is implemented in a similar way
	 */
	@Override
	public boolean paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.RED);
		//g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		if(goldWarningTimer != 0)
			g.drawString("Not Enough Gold", hitbox.x - 20, hitbox.y - 10);
		if(arrowWarningTimer != 0)
			g.drawString("Not Enough Arrows", hitbox.x - 20, hitbox.y - 10);
		return true;
	}
	/**
	 *resetGoldTimer() resets the goldtimer
	 */
	private void resetGoldTimer() {
		goldUseTimer = 100;
	}
	/**
	 *resetArrowDrawTimer() resets drawTimer
	 */
	private void resetArrowDrawTimer() {
		arrowDrawTimer = 100;
	}
}
