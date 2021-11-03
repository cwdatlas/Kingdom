import java.awt.Color;
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
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KingdomController extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
		private boolean gameRunning = false;
		private String enemySprite = "enemySprite.png";
		private String playerSprite = "playerSprite.png";
		private String arrowSprite = "arrowSprite.png";
		private String defenderSprite = "defenderSprite.png";
		private String wallSprite = "wallSprite.png";
		private Dimension panelDimensions;
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
		
		private int dayLength = 5000; //a full day at 5 min day should be around 30,000 frames
		private int defenders = 2;
		private int enemiesPerDay = 4;
		private int walls = 2;
		private int players = 1;
		private CollisionController colControl;
		
		private final Random random;
		
		//this shows the time of day as a sting under coinPanel
		JLabel tod = new JLabel();

		//this sets up the coins or score board
		JLabel coinPanel = new JLabel();
		
		ArrayList<BaseSprite> objectList = new ArrayList();

		public KingdomController() {
			this.random = new Random();
			// initialize objects and variables, could be moved into (initialize and remove)
			// section
			// only for 1 main character right now

			timeState = timeState.DAWN;

			panelDimensions = new Dimension();
			colControl = new CollisionController(objectList);

			this.add(coinPanel);

			this.add(tod);

		}

		public void paintComponent(Graphics g) {
			panelDimensions = this.getSize();
			
			if(timeState == TimeState.NIGHT) {
				g.setColor(java.awt.Color.blue);
			}
			else if(timeState == TimeState.DAY) {
				g.setColor(java.awt.Color.yellow);
			}
			else if(timeState == TimeState.DUSK) {
				g.setColor(java.awt.Color.green);
			}
			else if(timeState == TimeState.DAWN) {
				g.setColor(java.awt.Color.orange);
			}
			
			if(!gameRunning) { //spawn things that are dependent on frame width
				spawnPlayers(players);
				spawnDefenders(defenders);
				spawnWalls(walls);
				gameRunning = true;
			}
			
			if(panelDimensions!=null && gameRunning) { //then this runs the game
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
			//JLabels
			//this sets up the coins or score board

			coinPanel.setText("Coins: " + objectList.get(0).getGold());
			tod.setText("Time of Day: " + timeState );
			
		}

		// spawning shortcuts
		

		private void spawnPlayers(int numberOfPlayers) {
			for (int d = 0; d < numberOfPlayers; d++)
				objectList.add(new PlayableCharacter(((int)panelDimensions.getWidth() / 2), 500, 0, playerSprite, panelDimensions));
		}

		private void spawnDefenders(int numberOfDefenders) { // TODO set spawn parameters (place)
			for (int d = 0; d < numberOfDefenders; d++) {
				objectList.add(new Defender(800, 500, defenderSprite, panelDimensions));
			}
		}

		private void spawnEnemies(int numberOfEnemies) {// TODO set spawn parameters (place)
			for (int d = 0; d < numberOfEnemies; d++) {
				if (d%2 == 0)
					objectList.add(new Enemy(random.nextInt(300) -500, 500, enemySprite, panelDimensions));
				else
					objectList.add(new Enemy(random.nextInt(300) + (int)panelDimensions.getWidth(), 500, enemySprite, panelDimensions));
			}
		}

		private void spawnWalls(int numberOfWalls) {// TODO set spawn parameters (place)
			for (int d = 0; d < numberOfWalls; d++) {
				if (d%2 == 0)
				objectList.add(new Wall((int)panelDimensions.getWidth() / 3, 500, wallSprite, panelDimensions));
				else
				objectList.add(new Wall(((int)panelDimensions.getWidth() / 3)*2 , 500, wallSprite, panelDimensions));
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

					} else if (attacking) {
						((Enemy) objectList.get(i)).setAttack((PlayableCharacter)objectList.get(0));

					}
					objectList.get(i).move();
				}

				else if (objectList.get(i) instanceof Defender) { // TODO build mode guard and mode wonder in Defender
					
					if (roaming) {
						((Defender) objectList.get(i)).setRoaming();

						
					} else if (defending) {
						((Defender) objectList.get(i)).setDefending();

					}											
					objectList.get(i).move();
				} else if (objectList.get(i) instanceof Arrow) { // if class needs the move function

					objectList.get(i).move();
				}
			}
				attacking = false;
				defending = false;
				roaming = false;
				retreating = false;
		}
		
		private void checkCollisions() { //TODO collisions
			
			for(int i = 0; i<objectList.size();i++) {
				if(objectList.get(i) instanceof CollisionSprite) {
					((CollisionSprite) objectList.get(i)).checkCollision(colControl);
				}
			}	
		}
		
		private void timeCalculations() {
			if (timeOfDay > dayLength) {
				timeOfDay = 0;
				days++;

			}
			if(timeOfDay==dayLength*.7 && timeState==timeState.DUSK) {
				timeState=TimeState.NIGHT;
				spawning = true;
				attacking = true;

				
			}else if(timeOfDay==dayLength*.6 && timeState==timeState.DAY) {
				timeState=TimeState.DUSK;
				defending = true;

			}else if(timeOfDay==dayLength*.1 && timeState==timeState.DAWN) {
				timeState=TimeState.DAY;
				roaming = true;

			}else if(timeOfDay==0 && timeState==timeState.NIGHT) {
				timeState=TimeState.DAWN;
				retreating = true;

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