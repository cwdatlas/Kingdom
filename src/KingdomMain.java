//Programmed by Adrian and Aidan of Carroll college

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
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
		kingdom.addKeyListener((KeyListener) kingdomPanel);
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

	public static class KingdomPanel extends JPanel
			implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
		private boolean movingRight;
		private boolean movingLeft;
		private int defenders = 0;
		private int enemies = 0;
		private int walls = 0;
		private int players = 1;

		ArrayList<ObjectMain> objectList = new ArrayList();

		public KingdomPanel() {
			// initialize objects and variables, could be moved into (initialize and remove)
			// section
			// only for 1 main character right now
			spawnPlayers(players);
			spawnDefenders(defenders);
			spawnEnemies(enemies);
			spawnWalls(walls);

		}

		public void paintComponent(Graphics g) {
			// Move objects

			// lets objects move to their set locations

			for (int i = 0; i < objectList.size(); i++) {
				if (objectList.get(i).getClass() == PlayableCharacter.class)
																			
				{
					if (movingRight) {
						((PlayableCharacter) objectList.get(i)).moveRight();//telling the playable character each frame to keep moving if true
					} else if (movingLeft) {
						((PlayableCharacter) objectList.get(i)).moveLeft();
					}
				}

				else if (objectList.get(i).getClass() == Enemy.class || objectList.get(i).getClass() == Defender.class
						|| objectList.get(i).getClass() == Arrow.class) { // if class needs the move function
					objectList.get(i).move();
				}
			}

			// Collision detection and action

			
			
			// Painting objects on world panel
			for (int i = 0; i < objectList.size(); i++) {
				objectList.get(i).paint(g);
			}

			
			// checking to see if game is running, if not end game
			
			//this means we will have to have a key or a panel that comes up to have the ability to save and shut exit, or not save
			
			
			
			// initialize or remove objects

		}

		//spawning shortcuts
		
		private void spawnPlayers(int numberOfPlayers) {
			for (int d = 0; d < numberOfPlayers; d++)
				objectList.add(new PlayableCharacter(1200, 500, 0, "playerImage.png"));
		}
		private void spawnDefenders(int numberOfDefenders) {
			for (int d = 0; d < numberOfDefenders; d++) {
				objectList.add(new Defender(this.getWidth() / 2, 500));
			}
		}
		private void spawnEnemies(int numberOfEnemies) {
			for (int d = 0; d < numberOfEnemies; d++) {
				objectList.add(new Enemy(10, 500));
			}
		}
		private void spawnWalls(int numberOfWalls) {
			for (int d = 0; d < numberOfWalls; d++) {
				objectList.add(new Wall(this.getWidth() / 3, 500));
			}
		}

		
		//key and mouse listener events
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 39)
				movingRight = true;
			else if (e.getKeyCode() == 37)
				movingLeft = true;

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 39)
				movingRight = false;
			else if (e.getKeyCode() == 37)
				movingLeft = false;

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}