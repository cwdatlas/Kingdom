import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Aidan Scott and Adrien
 *Wall stops Enemies from moving through them, wile letting other sprites pass through
 */
public class Wall extends CollisionSprite {
	private Rectangle rangeHitBox;
	private double rangeWidthOfPanel = .3;
	protected int HP = 0;
	protected int infinityFrames;
	/**
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 */
	protected Wall(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
	}
	/**
	 * destroyWall setsVisible to false
	 */
	public void destroyWall() {
		this.setVisible(false);
	}
	/**
	 *rebuildWalls sets wall HP to 50
	 */
	public void rebuildWall() {
		HP = 50;
	}
	/**
	 *checkCollision asks colControl for any collisions with its hitbox
	 *then decides what to do with what it collides with
	 *@param CollisionController colControl
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
	 *paint overrides the paint function in BaseSprite 
	 *allowing a set of different colors to appear on the wall's
	 *border to show health
	 *@param Graphics g
	 */
	@Override
	public boolean paint(Graphics g) {
		super.paint(g);	
		if(HP <= 0) {
			g.setColor(new Color(1,1,1,250));
			g.setColor(Color.RED);
			g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		}
		else if (HP <= 15) {
			g.setColor(Color.RED);
			g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		}
				else if (HP <= 30) {
			g.setColor(Color.ORANGE);
			g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		}		
				else if(HP < 50) {
			g.setColor(Color.YELLOW);
			g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		}
				else if(HP == 50) {
			g.setColor(Color.MAGENTA);
			g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
		}
		return true;
	}
}
