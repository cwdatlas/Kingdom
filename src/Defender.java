import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
public class Defender extends BaseSprite implements DefenderI {
	private final Random random;

	protected Defender(int X, int Y, String fileName) {
		super(X, Y, fileName);
		random = new Random();
	}

	@Override
	public void setDefending() {
		goToCords[0] = random.nextInt(800);
	}

	@Override
	public void setRoaming() {
		if(Math.random() < .01) {
//		goToCords[0] = (int) (Math.random() * 1500);
			moveTo(random.nextInt(1500), goToCords[1] );
		}


	
	}
	
}
