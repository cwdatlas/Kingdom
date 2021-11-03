import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Arrow extends CollisionSprite {
	boolean right;
	boolean delete = false;

	protected Arrow(int x, int y, String fileName, Dimension panelDementions, boolean right) {
		super(x, y, fileName, panelDementions);
		dimensions = panelDementions;
		hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
		System.out.println("arrow Created");
	}

	@Override
	public boolean move() {
		boolean check = false;
		try {
			if (right) {
				currentPosition.setLocation(new Point(currentPosition.x + 1, currentPosition.y));
				hitbox.x = currentPosition.x;
			} else {
				currentPosition.setLocation(new Point(currentPosition.x - 1, currentPosition.y));
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
		ArrayList<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for (int i = 0; i < collidingSprites.size(); i++) {
			if (collidingSprites.get(i) instanceof Enemy) {
				Enemy effected = (Enemy) collidingSprites.get(i);
				effected.setRetreat();
				effected.setvisible(false);
				effected.placeSprite(effected.getSpawnPlace().x, effected.getSpawnPlace().y);
				delete = true;
			}

		}
		if (delete) {
			colControl.deleteObject(this);
		}
	}

}
