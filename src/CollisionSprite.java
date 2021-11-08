import java.awt.Dimension;

public abstract class CollisionSprite extends BaseSprite{

	protected CollisionSprite(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		// TODO Auto-generated constructor stub
	}

	abstract void checkCollision(CollisionController colControl);
	
	
}
