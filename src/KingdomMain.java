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
import java.util.Enumeration;
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
		private String enemySprite = "enemySprite.png";
		private String playerSprite = "playerSprite.png";
		private String arrowSprite = "arrowSprite.png";
		private String defenderSprite = "defenderSprite.png";
		private String wallSprite = "wallSprite.png";
		private int panelWidth;
		private boolean movingRight;
		private boolean movingLeft;
		//time variables
		private boolean day;
		private boolean night;
		private int days = 0;
		private int timeOfDay = 2000;
		private TimeState timeState;
		
		private int dayLength = 5000; //a full day at 5 min day should be around 30,000 frames
		private int defenders = 2;
		private int enemiesPerDay = 0;
		private int walls = 2;
		private int players = 1;

		ArrayList<BaseSprite> objectList = new ArrayList();

		public KingdomPanel() {
			// initialize objects and variables, could be moved into (initialize and remove)
			// section
			// only for 1 main character right now
			panelWidth = 1500;
			spawnPlayers(players);
			spawnDefenders(defenders);
			spawnWalls(walls);
			timeState = timeState.DAWN;

		}

		public void paintComponent(Graphics g) {
			boolean retreating = true;
			boolean defending = false;
			// Move objects

			// lets objects move to their set locations

			for (int i = 0; i < objectList.size(); i++) {
				if (objectList.get(i) instanceof PlayableCharacter) { // more efficient way of doing this class
																		// comparison
					if (movingRight) {
						((PlayableCharacter) objectList.get(i)).moveRight();// telling the playable character each frame
																			// to keep moving if true
					} else if (movingLeft) {
						((PlayableCharacter) objectList.get(i)).moveLeft();
					}
				}

				else if (objectList.get(i) instanceof Enemy) { //

					if (timeState==timeState.DAWN) {
						((Enemy) objectList.get(i)).setRetreat();
					//	retreating = true;
					} else if (timeState==timeState.NIGHT) {
						((Enemy) objectList.get(i)).setAttack((PlayableCharacter)objectList.get(0));
					//	retreating = false;
					}
					objectList.get(i).move();
				}

				else if (objectList.get(i) instanceof Defender) { // TODO build mode guard and mode wonder in Defender
					
					if (timeState==timeState.DAY) {
						((Defender) objectList.get(i)).setRoaming();
						
					} else if (timeState==timeState.DUSK) {
						((Defender) objectList.get(i)).setDefending();
					}											
					objectList.get(i).move();
				} else if (objectList.get(i) instanceof Arrow) { // if class needs the move function

					objectList.get(i).move();
				}
			}

			// TODO Collision detection and action
				
			for(int i = 0; i<objectList.size();i++)
				for(int a = 0; a<objectList.size() && a!=i;a++) { //TODO all combinations non repeatable
					if(objectList.get(i).isColliding(objectList.get(a)))
						if((objectList.get(i) instanceof Enemy && objectList.get(a) instanceof Arrow)||(objectList.get(i) instanceof Arrow && objectList.get(a) instanceof Enemy) ) {
							
						}
					
				}
			

			// Painting objects on world panel
			for (int i = 0; i < objectList.size(); i++) {
				objectList.get(i).paint(g);
			}

			// checking to see if game is running, if not end game

			// this means we will have to have a key or a panel that comes up to have the
			// ability to save and shut exit, or not save

			// TODO initialize or remove objects do whatever like the timer deal
			if (timeOfDay == dayLength * .60)
				spawnEnemies(enemiesPerDay);

			if (timeOfDay > dayLength) {
				timeOfDay = 0;
				days++;
			}
			if(timeOfDay==dayLength*.7) {
				timeState=timeState.NIGHT;
			}else if(timeOfDay==dayLength*.6) {
				timeState=timeState.DUSK;
			}else if(timeOfDay==dayLength*.1) {
				timeState=timeState.DAY;
			}else if(timeOfDay==0) {
				timeState=timeState.DAWN;
			}
			timeOfDay++;

		}

		// spawning shortcuts

		private void spawnPlayers(int numberOfPlayers) {
			for (int d = 0; d < numberOfPlayers; d++)
				objectList.add(new PlayableCharacter((panelWidth / 2), 500, 0, playerSprite));
		}

		private void spawnDefenders(int numberOfDefenders) { // TODO set spawn parameters (place)
			for (int d = 0; d < numberOfDefenders; d++) {
				objectList.add(new Defender(panelWidth / 2, 500, defenderSprite));
			}
		}

		private void spawnEnemies(int numberOfEnemies) {// TODO set spawn parameters (place)
			for (int d = 0; d < numberOfEnemies; d++) {
				if (Math.random() >= .5)
					objectList.add(new Enemy((int) (Math.random() * 300) - 800, 500, enemySprite));
				else
					objectList.add(new Enemy((int) (Math.random() * 100) + panelWidth, 500, enemySprite));
			}
		}

		private void spawnWalls(int numberOfWalls) {// TODO set spawn parameters (place)
			for (int d = 0; d < numberOfWalls; d++) {
				objectList.add(new Wall(panelWidth / 3, 500, wallSprite));
				objectList.add(new Wall((panelWidth / 3)*2 , 500, wallSprite));
			}
		}
		
		private enum TimeState{
			DUSK,
			DAY,
			DAWN,
			NIGHT
		}

		// key and mouse listener events

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