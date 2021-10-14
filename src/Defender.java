import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
public class Defender extends BaseSprite implements DefenderI{
	
	protected Defender(int X, int Y, String fileName) {
		super(X, Y, fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean setDefending() {
		boolean check = false;
		try {
			goToCords[0] = 200;
			check = true;
		}
		finally {
			
		}
		return check;
	}

	@Override
	public boolean setRoaming() {
		boolean check = false;
		try {
			Timer myTimer = new Timer();
			myTimer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					goToCords[0] = (int)(Math.random() * 100);
				}
			}, 4000, 4000);
			
			check = true;
			
		}
		finally {
			
		}
		return check;
	}
}
