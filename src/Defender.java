
import java.awt.Dimension;

import java.awt.Point;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
public class Defender extends CollisionSprite implements DefenderI {
	private final Random random;

	protected Defender(int x, int y, String fileName, Dimension panelDementions) {
		super(x, y, fileName, panelDementions);
		random = new Random();
	}

	@Override
	public void setDefending() {
//		goToCords[0] = random.nextInt(800);
		target.setLocation(new Point(random.nextInt(800), 500));

	}

	@Override
	public void setRoaming() {
		if(Math.random() < .01) {
		//pass 1500 as a parameter in the constructor
			moveTo(random.nextInt(1500), (int) target.getY() );
		}


	
	}

	@Override
	void checkCollision(CollisionController colControl) {
		// TODO Auto-generated method stub
		
	}
	
}
