import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Arrow extends CollisionSprite {

	protected Arrow(int x, int y, String fileName, Dimension panelDementions) {
		super(x, y, fileName, panelDementions);
		hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}

	@Override
	void checkCollision(CollisionController colControl) {
		ArrayList<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for (int i = 0; i < collidingSprites.size(); i++) {
			if (collidingSprites.get(i) instanceof Enemy) {
				Enemy effected = (Enemy) collidingSprites.get(i);
				effected.setRetreat();
				effected.setvisible(false);
				effected.placeSprite(effected.getSpawnPlace().x, effected.getSpawnPlace().y);
				colControl.deleteObject(this);
			}
		}

	}

}
