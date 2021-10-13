import java.io.IOException;

import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college

public class PlayableCharacter extends BaseSprite implements PlayableCharacterI {
	// set your varables here, make sure as many as possible are private
	private int gold;

	// constructor...
	PlayableCharacter(int X, int Y, int Gold, String fileName) {
		super(X, Y);
		x = X;
		y = Y;
		gold = Gold;
		this.loadImage(fileName);

	}

	@Override
	public boolean moveLeft() {
		x= x-1;
		return true; //should be moving at a rate of 100 pixels per second as it is timed to the frame rate 
	}

	@Override
	public boolean moveRight() {
		x=x+1;
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

}
