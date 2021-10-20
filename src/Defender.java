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
		goToCords[0] = 200;
	}

	@Override
	public void setRoaming() {
		Timer myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (Math.random() < 0.1) {
					// put a more specific are for them to roam in
					goToCords[0] = (int) (Math.random() * 100);
				}
			}
		}, 4000, 4000);
	}
}
