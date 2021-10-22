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

	public static class KingdomPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
		private boolean gameRunning = false;
		private String enemySprite = "enemySprite.png";
		private String playerSprite = "playerSprite.png";
		private String arrowSprite = "arrowSprite.png";
		private String defenderSprite = "defenderSprite.png";
		private String wallSprite = "wallSprite.png";
		private Dimension panelDementions;
		private boolean movingRight;
		private boolean movingLeft;
		//time variables

		private int days = 0;
		private int timeOfDay = 0;
		private TimeState timeState;
		boolean spawning=false;
		boolean defending=false;
		boolean roaming=true;
		boolean attacking=false;
		boolean retreating = false;
		
		private int dayLength = 3000; //a full day at 5 min day should be around 30,000 frames
		private int defenders = 2;
		private int enemiesPerDay = 4;
		private int walls = 2;
		private int players = 1;
		
		private final Random random;

		ArrayList<BaseSprite> objectList = new ArrayList();

		public KingdomPanel() {
			this.random = new Random();
			// initialize objects and variables, could be moved into (initialize and remove)
			// section
			// only for 1 main character right now

			timeState = timeState.DAWN;
			panelDementions = new Dimension();
			

		}

		public void paintComponent(Graphics g) {
			panelDementions = this.getSize();
			
			if(!gameRunning) { //spawn things that are dependent on frame width
				spawnPlayers(players);
				spawnDefenders(defenders);
				spawnWalls(walls);
				gameRunning = true;
			}
			
			if(panelDementions!=null && gameRunning) { //then this runs the game
			doMoves();
			
			checkCollisions(); // TODO Collision detection and action

			for (int i = 0; i < objectList.size(); i++) { // Painting objects on world panel
				objectList.get(i).paint(g);
			}

			timeCalculations();
			
			if (spawning) {   // TODO initialize or remove objects do whatever like the timer deal
				spawnEnemies(enemiesPerDay);
				spawning = false;
			}
			}
		}

		// spawning shortcuts

		private void spawnPlayers(int numberOfPlayers) {
			for (int d = 0; d < numberOfPlayers; d++)
				objectList.add(new PlayableCharacter(((int)panelDementions.getWidth() / 2), 500, 0, playerSprite, panelDementions));
		}

		private void spawnDefenders(int numberOfDefenders) { // TODO set spawn parameters (place)
			for (int d = 0; d < numberOfDefenders; d++) {
				objectList.add(new Defender(800, 500, defenderSprite, panelDementions));
			}
		}

		private void spawnEnemies(int numberOfEnemies) {// TODO set spawn parameters (place)
			for (int d = 0; d < numberOfEnemies; d++) {
				if (d%2 == 0)
					objectList.add(new Enemy(random.nextInt(300) -300, 500, enemySprite, panelDementions));
				else
					objectList.add(new Enemy(random.nextInt(300) + (int)panelDementions.getWidth(), 500, enemySprite, panelDementions));
			}
		}

		private void spawnWalls(int numberOfWalls) {// TODO set spawn parameters (place)
			for (int d = 0; d < numberOfWalls; d++) {
				objectList.add(new Wall((int)panelDementions.getWidth() / 3, 500, wallSprite, panelDementions));
				objectList.add(new Wall(((int)panelDementions.getWidth() / 3)*2 , 500, wallSprite, panelDementions));
			}
		}
		
		private enum TimeState{ //TODO this needs to be before JPanel/ before timestate is initialize
			DUSK,
			DAY,
			DAWN,
			NIGHT
		}
		
		private void doMoves() {
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

				else if (objectList.get(i) instanceof Enemy) { 

					if (retreating) {
						((Enemy) objectList.get(i)).setRetreat();
						retreating=false;
					} else if (attacking) {
						((Enemy) objectList.get(i)).setAttack((PlayableCharacter)objectList.get(0));
						attacking = false;
					}
					objectList.get(i).move();
				}

				else if (objectList.get(i) instanceof Defender) { // TODO build mode guard and mode wonder in Defender
					
					if (roaming) {
						((Defender) objectList.get(i)).setRoaming();
						roaming=false;
						
					} else if (defending) {
						((Defender) objectList.get(i)).setDefending();
						defending = false;
					}											
					objectList.get(i).move();
				} else if (objectList.get(i) instanceof Arrow) { // if class needs the move function

					objectList.get(i).move();
				}
			}
		}
		
		private void checkCollisions() { //TODO collisions
			
			for(int i = 0; i<objectList.size();i++)
				for(int a = 0; a<objectList.size() && a!=i;a++) { //TODO all combinations non repeatable
					if(objectList.get(i).isColliding(objectList.get(a)))
						if((objectList.get(i) instanceof Enemy && objectList.get(a) instanceof Arrow)||(objectList.get(i) instanceof Arrow && objectList.get(a) instanceof Enemy) ) {
							
						}
					
				}
		}
		
		private void timeCalculations() {
			if (timeOfDay > dayLength) {
				timeOfDay = 0;
				days++;

			}
			if(timeOfDay==dayLength*.7 && timeState==timeState.DUSK) {
				timeState=timeState.NIGHT;
				spawning = true;
				attacking = true;
			}else if(timeOfDay==dayLength*.6 && timeState==timeState.DAY) {
				timeState=timeState.DUSK;
				retreating = true;
			}else if(timeOfDay==dayLength*.1 && timeState==timeState.DAWN) {
				timeState=timeState.DAY;
				roaming = true;
			}else if(timeOfDay==0 && timeState==timeState.NIGHT) {
				timeState=timeState.DAWN;
				defending = true;
			}
			timeOfDay++;
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