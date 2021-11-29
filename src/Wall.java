import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Aidan Scott and Adrien
 */
public class Wall extends CollisionSprite {
	private Rectangle rangeHitBox;
	private double rangeWidthOfPanel = .3;
	protected int HP = 0;
	protected int infinityFrames;
	private boolean broken0 = true;
	private boolean broken1 = true;
	private boolean broken2 = true;
	private boolean broken3 = true;
	private boolean broken4 = true;
	private boolean broken5 = true;
	/**
	 * @param x is the x position, top left, of where the sprite will be spawned
	 * @param fileName is the filename of the image that will be used for the sprite
	 * @param panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 */
	protected Wall(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
	}
	/**
	 * destroyWall() setsVisible to false
	 */
	public void destroyWall() {
		this.setVisible(false);
	}
	/**
	 *rebuildWalls() sets wall HP to 50
	 */
	public void rebuildWall() {
		HP = 50;
		broken0 = true;
	}
	/**
	 *checkCollision() asks colControl for any collisions with its hitbox
	 *then decides what to do with what it collides with
	 *@param colControl
	 *@see CollisionController see see how collision works
	 */
	@Override
	public void checkCollision(CollisionController colControl) {
		List<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for (int i = 0; i < collidingSprites.size(); i++) {
			if (collidingSprites.get(i) instanceof Enemy) {
				if (HP > 0  && (infinityFrames>60)) {
					infinityFrames = 0;
					HP--;
					if (HP == 0) {
						destroyWall();
					}
				}
				collidingSprites.get(i).setBlocked(HP > 0);
				infinityFrames++;
			}
		}
	}
	/**
	 *paint() overrides the paint function in BaseSprite 
	 *allowing a set of different colors to appear on the wall's
	 *border to show health
	 *@param g
	 */
	@Override
	public boolean paint(Graphics g) {
		super.paint(g);	
		if(HP == 0 && broken5) {
			this.loadImage("wallSpriteBroken.png");
			broken5 = false;
			broken0 = true;
		}
		else if (HP == 10 && broken4) {
			this.loadImage("wallSprite4.png");
			broken4 = false;
			broken5 = true;
		}
		else if (HP == 20 && broken3) {
			this.loadImage("wallSprite3.png");
			broken3 = false;
			broken4 = true;
		}
		else if (HP == 30 && broken2) {
			this.loadImage("wallSprite2.png");
			broken2 = false;
			broken3 = true;
		}		
		else if(HP == 40 && broken1) {
			this.loadImage("wallSprite1.png");
			broken1 = false;
			broken2 = true;
		}
		else if(HP == 50 && broken0) {
			this.loadImage("wallSprite.png");
			broken0 = false;
			broken1 = true;
		}
		return true;
	}
}
