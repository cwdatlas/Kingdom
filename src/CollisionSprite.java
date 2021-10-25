import java.awt.Dimension;

public abstract class CollisionSprite extends BaseSprite{

	protected CollisionSprite(int x, int y, String fileName, Dimension panelDementions) {
		super(x, y, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}

	abstract void checkCollision(CollisionController colControl);
	
	
}
