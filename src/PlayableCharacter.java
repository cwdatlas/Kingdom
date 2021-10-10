import java.io.IOException;

import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college

public class PlayableCharacter extends ObjectMain implements PlayableCharacter_Interface {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveRight() {
		// TODO Auto-generated method stub
		return false;
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
