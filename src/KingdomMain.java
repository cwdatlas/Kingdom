//Programmed by Adrian and Aidan of Carroll college

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class KingdomMain {

	public static void main(String[] args) {
		// Initializing variables and objects

		JFrame kingdom = new JFrame("Kingdom");
		JPanel kingdomPanel = new KingdomPanel();

		// setting window params
		try {
			// setting up parameters for the JFrame
			kingdom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			kingdom.setVisible(true);
			kingdom.setBounds(0, 0, 1920, 1080);
			kingdom.setVisible(true);

			// setting parameters for the JPanel: world
			kingdomPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
			kingdomPanel.setBackground(new Color(200, 200, 200));

//		mListener = new Mouse_Listener(world);
//		keyListener = new Key_Listener(main, world);

			// adding panel to kingdom
			kingdom.add(kingdomPanel);

			Timer myTimer = new Timer();
			myTimer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					kingdom.repaint();
				}
				// setting
			}, 10, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class KingdomPanel extends JPanel {
		ObjectMain player;

		public KingdomPanel() {
			// initialize objects and variables
			player = new PlayableCharacter(40, 40, 0, "playerImage.png");
		}

		public void paintComponent(Graphics g) {
			// Move objects

			// Collision detection and action

			// Painting objects on world panel
			boolean didDraw = player.paint(g);
			// if (didDraw)
			// System.out.println("drawing was a success");
			// else
			// System.out.println("drawing was a failure");

			// checking to see if game is running, if not end game

			// initialize or remove objects
		}

	}

}