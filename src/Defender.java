import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
public class Defender extends BaseSprite implements DefenderI {
	
	double a = (Math.random() * 10);
	
	

	

	
	private final Random random;

	protected Defender(int x, int y, String fileName, Dimension panelDementions) {
		super(x, y, fileName, panelDementions);
		random = new Random();
	}

	@Override
	public void setDefending() {
//		goToCords[0] = random.nextInt(800);
//		target.setLocation(new Point(random.nextInt(800), 500));
//		target.setLocation(new Point((int)(dimensions.getWidth() / 3 + 50 + random.nextInt(30)), 500));
		target.setLocation(new Point((int)(600 + random.nextInt(200)), 500));


	}

	@Override
	public void setRoaming() {

				if(Math.random() < .01) {
					
					
					//pass 1500 as a parameter in the constructor
						moveTo(random.nextInt(1500), (int) target.getY() );
					}				
	}
			
	public void setMode() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(Math.random() < .99) {
					setDefending();
				}
				else {
					setRoaming();
				}
			
			}},10, 500);
		
	
		
	
	}
	}

