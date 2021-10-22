import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
public class Defender extends BaseSprite implements DefenderI {

	protected Defender(int X, int Y, String fileName) {
		super(X, Y, fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDefending() {
		goToCords[0] = 800;
	}

	@Override
	public void setRoaming() {
		goToCords[0] = (int) (Math.random() * 100);

	
	}
	
}
