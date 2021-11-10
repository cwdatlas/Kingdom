import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arrow extends CollisionSprite {
	boolean right;
	boolean delete = false;

	Random random = new Random();
	

	protected Arrow(int x, String fileName, Dimension panelDementions, boolean goingRight) {
		super(x, fileName, panelDementions);
		dimensions = panelDementions;
		int y = currentPosition.y + 30;
		currentPosition.y = y;
		hitbox = new Rectangle(x, currentPosition.y, img.getWidth(), img.getHeight());
		right = goingRight;
	}

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
