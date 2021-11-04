import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

//Programmed by Adrian and Aidan of Carroll college
public class Wall extends CollisionSprite {
	private Rectangle rangeHitBox;
	private double rangeWidthOfPanel = .3;
	protected int HP = 50;
	protected int infinityFrames;

	protected Wall(int X, int Y, String fileName, Dimension panelDementions) {
		super(X, Y, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}

	private void destroyWall() {
		this.setvisible(false);
	}

	@Override
	public void checkCollision(CollisionController colControl) {
		ArrayList<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
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
			//if player next to wall put up the gold required for wall
			//add gold counter top right corner when wall is being built
//			else if(collidingSprites.get(i) instanceof PlayableCharacter) {
//				 
//			}
 {
				
			}

		}
	}
	
	@Override
	public boolean paint(Graphics g) {
		super.paint(g);
		if(HP == 50) {
			g.setColor(Color.BLUE);
			g.drawRect(hitbox.x, hitbox.y, hitbox.height, hitbox.width);
		}
		
		else if(HP <= 50) {
			g.setColor(Color.YELLOW);
			g.drawRect(hitbox.x, hitbox.y, hitbox.height, hitbox.width);
		}
		
		else if (HP <= 30) {
			g.setColor(Color.ORANGE);
			g.drawRect(hitbox.x, hitbox.y, hitbox.height, hitbox.width);
		}
		
		else if (HP <= 15) {
			g.setColor(Color.RED);
			g.drawRect(hitbox.x, hitbox.y, hitbox.height, hitbox.width);
		}

		
		return true;
	
	
	}

}
