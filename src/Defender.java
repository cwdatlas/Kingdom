import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
	
public class Defender extends CollisionSprite implements DefenderI {
	private final Random random;
	private boolean roaming;
	int timerTick = 0;


	protected Defender(int x, int y, String fileName, Dimension panelDementions) {
		super(x, y, fileName, panelDementions);
		random = new Random();
	}

	@Override
	public void setDefending() {
		roaming = false;
//		goToCords[0] = random.nextInt(800);
//		target.setLocation(new Point(random.nextInt(800), 500));
//		target.setLocation(new Point((int)(dimensions.getWidth() / 3 + 50 + random.nextInt(30)), 500));
		target.setLocation(new Point((int)(600 + random.nextInt(200)), 500));


	}

	@Override
	public void setRoaming() {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timerTick++;
				System.out.println(timerTick);
				if(roaming) {
					if(Math.random() < .09) {
						moveTo(random.nextInt(1500), (int) target.getY() );
					}
				}
			
			}},50, 10000 );
	}
					

	public void checkCollision(CollisionController colControl) {
		// TODO Auto-generated method stub
		
	}


	}

