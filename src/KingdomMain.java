import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class KingdomMain {

	public static void main(String[] args) {
		JFrame kingdom = new JFrame("Kingdom");
		JPanel world = new JPanel();

		try {
			// setting up parameters for the JFrame
			kingdom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			kingdom.setVisible(true);
			kingdom.setBounds(0, 0, 1920, 1080);
			kingdom.setVisible(true);

			// setting parameters for the JPanel: world
			world.setBorder(new EmptyBorder(0, 0, 0, 0));
			world.setBackground(new Color(200, 200, 200));

//		mListener = new Mouse_Listener(world);
//		keyListener = new Key_Listener(main, world);

			// adding classes to kingdom
			kingdom.add(world);

			Timer myTimer = new Timer();
			myTimer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					kingdom.repaint();
				}
			}, 10, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}