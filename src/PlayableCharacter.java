
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college

public class PlayableCharacter extends CollisionSprite implements PlayableCharacterI {
	// set your varables here, make sure as many as possible are private
	Random random = new Random();
	boolean delete = false;

	// constructor...
	PlayableCharacter(int X, int Y, int Gold, String fileName, Dimension panelDementions) {
		super(X, Y, fileName, panelDementions);
	}

	@Override
	public boolean moveLeft() {
//		currentPosition[0]= currentPosition[0]-2;
		currentPosition.setLocation(new Point((int)currentPosition.getX() - 2, 500));
		return true; //should be moving at a rate of 100 pixels per second as it is timed to the frame rate 
	}

	@Override
	public boolean moveRight() {
		currentPosition.setLocation(new Point((int)currentPosition.getX() + 2, 500));
//		currentPosition[0]=currentPosition[0]+2;
		return true;
	}

	@Override
	public boolean stopMoving() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean useMoney(boolean use) {
		// TODO Auto-generated method stub
		return false;
	}
	
		//picking up coins
		public void checkCollision(CollisionController colControl) {
			ArrayList<BaseSprite> collidingSprites = colControl.checkCollition(this.getHitBox());
			for (int i = 0; i < collidingSprites.size(); i++) {
				if (collidingSprites.get(i) instanceof DroppedCoin) {
					DroppedCoin coin = (DroppedCoin) collidingSprites.get(i);
					colControl.deleteObject(coin);
					this.incrementGold();
				}
	}	
}
}
