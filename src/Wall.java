import java.awt.Dimension;
import java.util.ArrayList;

//Programmed by Adrian and Aidan of Carroll college
public class Wall extends CollisionSprite{
	protected int HP = 50;
	
	protected Wall(int X, int Y, String fileName,Dimension panelDementions) {
		super(X, Y, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}
	
	private void destroyWall() {
		this.setvisible(false);
	}

	@Override
	public void checkCollision(CollisionController colControl) {
		ArrayList<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
		for(int i = 0; i < collidingSprites.size(); i++) {
			if(collidingSprites.get(i) instanceof Enemy && HP != 0) {
				if(HP > 0)
				collidingSprites.get(i).blocked = true;
				System.out.println("enemy blocked " + HP);
				HP--;
				if(HP==0)
					destroyWall();
			}
			else {
				collidingSprites.get(i).blocked = false;
			}
		}
	}

}
