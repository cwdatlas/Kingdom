import java.awt.Dimension;
import java.util.ArrayList;

//Programmed by Adrian and Aidan of Carroll college
public class Wall extends CollisionSprite {
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
				
				if (HP > 0  && (infinityFrames>100000)) {
					
					infinityFrames = 0;
					HP--;
					if (HP == 0) {
						destroyWall();
					}
				}
				collidingSprites.get(i).blocked = HP > 0;
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

}
