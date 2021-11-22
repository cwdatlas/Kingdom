import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
/**
 * @author Aidan Scott & Adrien
 * @see KingdomController to see the bulk of the game
 */
public class KingdomMain {
	public static void main(String[] args) {
		JFrame kingdom = new JFrame("Kingdom");
		JPanel kingdomPanel = new KingdomController(kingdom);
		kingdom.addKeyListener((KeyListener) kingdomPanel);
		kingdom.addMouseListener((MouseListener) kingdomPanel);
		try {
			// setting up parameters for the JFrame
			kingdom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			kingdom.setVisible(true);
			kingdom.setBounds(0, 0, 1920, 1080);
			kingdom.setVisible(true);
			kingdom.setBackground(Color.WHITE);
			// setting parameters for the JPanel: world
			kingdomPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
			// adding panel to kingdom
			kingdom.add(kingdomPanel);
			Timer myTimer = new Timer();
			myTimer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					kingdom.repaint();
				}
				// setting
			}, 0, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}