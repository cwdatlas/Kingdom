
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college

public class PlayableCharacter extends CollisionSprite implements PlayableCharacterI {
	// set your varables here, make sure as many as possible are private
	private Random random = new Random();
	private boolean downPress = false;
	private boolean playerShooting = false;
	private int goldUseTimer = 0;
	private int droppedGoldTimer = 0;
	private int arrowDrawTimer = 0;
	private int arrows = 0;
	private int arrowTarget = 0;

	// constructor...
	PlayableCharacter(int x, int Gold, int arrowsGiven, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		arrows = arrowsGiven;
		this.gold = Gold;
	}

	@Override
	public boolean moveLeft() {
		currentPosition.setLocation(new Point((int) currentPosition.getX() - 2, currentPosition.y));
		hitbox.setLocation(new Point((int) currentPosition.getX() - 2, currentPosition.y));
		return true;
	}

	@Override
	public boolean moveRight() {
		currentPosition.setLocation(new Point((int) currentPosition.getX() + 2, currentPosition.y));
		hitbox.setLocation(new Point((int) currentPosition.getX() + 2, currentPosition.y));
		return true;
	}

	@Override
	public void downPress(boolean press) {
		downPress = press;
	}
	public void playerShoot(boolean shooting, int position) {
		playerShooting = shooting;
		arrowTarget = position;
	}
	public int getArrows() {
		return arrows;
	}

	// picking up coins
	public void checkCollision(CollisionController colControl) {
		List<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for (int i = 0; i < collidingSprites.size(); i++) {
			if (collidingSprites.get(i) instanceof DroppedCoin && droppedGoldTimer == 0) {
				DroppedCoin coin = (DroppedCoin) collidingSprites.get(i);
				colControl.deleteObject(coin);
				this.incrementGold();
			} else if (collidingSprites.get(i) instanceof Enemy) {
				colControl.deleteObject(this);
			} else if (collidingSprites.get(i) instanceof Wall) {
				if (downPress && this.getGold() >= 3 && goldUseTimer == 0) {
					Wall wall = (Wall) collidingSprites.get(i);
					if (wall.HP < 50) {
						wall.rebuildWall();
						this.setGold(this.getGold() - 3);
						resetGoldTimer();
					}
				}
			}else if(collidingSprites.get(i) instanceof Shop && goldUseTimer == 0) {
				Shop shop = (Shop) collidingSprites.get(i);
				if(shop.getShopType() == ShopType.arrowType) {
					if(downPress && this.getGold() >= 2) {
						arrows++;
						this.setGold(this.getGold() - 2);
						resetGoldTimer();
					}
					
				}else if(shop.getShopType() == ShopType.defenderType) {
					if(downPress && this.getGold() >= 3) {
						colControl.addObject(new Defender(800, "defenderSprite.png", dimensions));
						this.setGold(this.getGold() - 3);
						resetGoldTimer();
					}
				}
			}
		}
		//press down to drop gold
		if (downPress && gold > 0 && goldUseTimer == 0) {
			this.gold--;
			colControl.addObject(new DroppedCoin(currentPosition.x, "coin.png", dimensions));
			resetGoldTimer();
			droppedGoldTimer = 200;
		}
		//player shoots arrow
		if (playerShooting && arrows > 0 && arrowDrawTimer == 0) {
			this.arrows--;
			Arrow arrow = new Arrow(currentPosition.x, "arrowSprite.png", dimensions, currentPosition.x < arrowTarget);
			arrow.moveTo(target.x, 500);
			colControl.addObject(arrow);
			resetArrowDrawTimer();
		}
		// incrementing timers down
		if (goldUseTimer > 0)
			goldUseTimer--;
		if (droppedGoldTimer > 0)
			droppedGoldTimer--;
		if (arrowDrawTimer > 0)
			arrowDrawTimer--;
	}

	@Override
	public boolean paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.RED);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		return true;

	}
	//centralized place to reset timers
	private void resetGoldTimer() {
		goldUseTimer = 100;
	}
	private void resetArrowDrawTimer() {
		arrowDrawTimer = 100;
	}
}
