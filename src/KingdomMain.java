//Programmed by Adrian and Aidan of Carroll college

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


public class KingdomMain {

	public static void main(String[] args) {
		// Initializing variables and objects
		 int coins = 30;

		JFrame kingdom = new JFrame("Kingdom");
		JPanel kingdomPanel = new KingdomController();
		kingdom.addKeyListener((KeyListener) kingdomPanel);
		//this sets up the coins or score board
		JLabel coinPanel = new JLabel();
		coinPanel.setText("Coins: " + coins);
		kingdomPanel.add(coinPanel);
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



}