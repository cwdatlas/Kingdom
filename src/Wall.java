import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

//Programmed by Adrian and Aidan of Carroll college
public class Wall extends CollisionSprite {
	private Rectangle rangeHitBox;
	private double rangeWidthOfPanel = .3;
	protected int HP = 50;
	protected int infinityFrames;

	protected Wall(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}

	public void destroyWall() {
		this.setvisible(false);
	}
	
	public void rebuildWall() {
		HP = 50;
	}

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
