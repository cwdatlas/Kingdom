import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class KingdomController extends JPanel
		implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, ActionListener{
	private boolean gameRunning = false;
	private boolean spawnedSprites = false;
	private String enemySprite = "enemySprite.png";
	private String playerSprite = "playerSprite.png";
	private String arrowSprite = "arrowSprite.png";
	private String defenderSprite = "defenderSprite.png";
	private String wallSprite = "wallSprite.png";
	private Dimension panelDimensions;
	private boolean movingRight;
	private boolean movingLeft;
	private boolean playerUseMoney;
	// time variables
	private int days = 0;
	private int timeOfDay = 0;
	private TimeState timeState;
	private boolean spawning = false;
	private boolean defending = false;
	private boolean roaming = true;
	private boolean attacking = false;
	private boolean retreating = false;
	private Color dawnColor = new Color(255, 153, 51,30);
	private Color dayColor = new Color(6,6,6,0);
	private Color duskColor = new Color(255, 153, 51,30);
	private Color nightColor = new Color(0, 0, 0,120);
	private Color backgroundColor = new Color(128, 255, 191,30);
	private BufferedImage backgroundImage; 
	
	private int dayLength = 5000; // a full day at 5 min day should be around 30,000 frames
	private int defenders = 2;
	private int enemiesPerDay = 4;
	private int walls = 2;
	private int players = 1;
	private CollisionController colControl;

	private final Random random;
	
	JFrame parent;
	
	private JPanel startWindow = new JPanel();
	private boolean startWindowVisible = true;
	private JButton startGame = new JButton();
	private JPanel youDiedWindow = new JPanel();
	private boolean youDiedVisible = false;
	private JButton playAgain = new JButton();

	JLabel tod = new JLabel(); // this shows the time of day as a sting under coinPanel
	JLabel coinPanel = new JLabel(); // this sets up the coins or score board
	List<BaseSprite> objectList = new ArrayList();

	public KingdomController(JFrame parentPanel) {
		parent = parentPanel;
		this.random = new Random();

		timeState = timeState.DAWN;
		panelDimensions = new Dimension();
		colControl = new CollisionController(objectList);
		this.add(coinPanel);
		this.add(tod);
		
		try {
			backgroundImage = ImageIO.read(this.getClass().getResource("/images/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Panel parameters
		//startWindow params
		startWindow.setBackground(Color.BLACK);
		TitledBorder log2;
		log2 = BorderFactory.createTitledBorder("Start Game");
		log2.setTitleColor(Color.WHITE);
		log2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		startWindow.setBorder(log2);
		startWindow.setLayout(null);
		this.add(startWindow);
		
		//youDiedWindow
		youDiedWindow.setBackground(Color.BLACK);
		TitledBorder log3;
		log3 = BorderFactory.createTitledBorder("You Died");
		log3.setTitleColor(Color.WHITE);
		log3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		youDiedWindow.setBorder(log3);
		youDiedWindow.setLayout(null);
		this.add(youDiedWindow);
		
		//buttons
		startGame.setText("START");
		startGame.setBackground(Color.WHITE);
		startGame.setVisible(true);
		startGame.addActionListener(this);
		startWindow.add(startGame);
	}
	public void paintComponent(Graphics g) {
		panelDimensions = this.getSize();
		
		startWindow.setBounds(panelDimensions.width/3, (int)(panelDimensions.height*.1), panelDimensions.width/3, (int)(panelDimensions.height*.7));
		startWindow.setVisible(startWindowVisible);
		startGame.setBounds((int)(startWindow.getWidth()*.3), (int)(startWindow.getHeight()*.8), (int)(startWindow.getWidth()*.4), (int)(startWindow.getHeight()*.1));
		
		youDiedWindow.setBounds(panelDimensions.width/3, (int)(panelDimensions.height*.1), panelDimensions.width/3, (int)(panelDimensions.height*.7));
		youDiedWindow.setVisible(youDiedVisible);
		
		if (!gameRunning
			&& !spawnedSprites) { // spawn things that are dependent on frame width
			spawnPlayers(players);
			spawnDefenders(defenders);
			spawnWalls(walls);
			spawnedSprites = true;
		}

		if (panelDimensions != null 
			&& gameRunning) { // then this runs the game
			doMoves();
			checkCollisions();
			
			g.drawImage(backgroundImage, 0, 0, panelDimensions.width, panelDimensions.height, null);
			g.setColor(backgroundColor);
			g.fillRect(0, 0, panelDimensions.width, panelDimensions.height);
			for (int i = 0; i < objectList.size(); i++) { // Painting objects on world panel
				objectList.get(i).paint(g);
			}

			timeCalculations();

			if (spawning) { // TODO initialize or remove objects do whatever like the timer deal
				spawnEnemies(enemiesPerDay);
				spawning = false;
			}
		}
		// JLabels
		// this sets up the coins or score board
		coinPanel.setText("Coins: " + objectList.get(0).getGold());
		tod.setText("Time of Day: " + timeState);
		coinPanel.setForeground(Color.WHITE);
		tod.setForeground(Color.WHITE);
	}

	// spawning shortcuts
	//spawning players
	private void spawnPlayers(int numberOfPlayers) {
		for (int d = 0; d < numberOfPlayers; d++)
			objectList.add(new PlayableCharacter(((int) panelDimensions.getWidth() / 2), 500, 0, playerSprite,
					panelDimensions));
	}
	//spawning defenders
	private void spawnDefenders(int numberOfDefenders) { // TODO set spawn parameters (place)
		for (int d = 0; d < numberOfDefenders; d++) {
			objectList.add(new Defender(800, 500, defenderSprite, panelDimensions));
		}
	}
	//spawning Enemies
	private void spawnEnemies(int numberOfEnemies) {// TODO set spawn parameters (place)
		for (int d = 0; d < numberOfEnemies; d++) {
			if (d % 2 == 0)
				objectList.add(new Enemy(random.nextInt(300) - 500, 500, enemySprite, panelDimensions));
			else
				objectList.add(new Enemy(random.nextInt(300) + (int) panelDimensions.getWidth(), 500, enemySprite,
						panelDimensions));
		}
	}
	//spawning walls
	private void spawnWalls(int numberOfWalls) {// TODO set spawn parameters (place)
		for (int d = 0; d < numberOfWalls; d++) {
			if (d % 2 == 0)
				objectList.add(new Wall((int) panelDimensions.getWidth() / 3, 500, wallSprite, panelDimensions));
			else
				objectList.add(new Wall(((int) panelDimensions.getWidth() / 3) * 2, 500, wallSprite, panelDimensions));
		}
	}


	//set positions to move and allow the sprites to move themselves to thier target positions
	private void doMoves() {
		int defender = 0;
		for (int i = 0; i < objectList.size(); i++) {
			if (objectList.get(i) instanceof PlayableCharacter) { // more efficient way of doing this class
																	// comparison
				if (movingRight) {
					((PlayableCharacter) objectList.get(i)).moveRight();// telling the playable character each frame
																		// to keep moving if true
				} else if (movingLeft) {
					((PlayableCharacter) objectList.get(i)).moveLeft();
				}
				((PlayableCharacter) objectList.get(i)).downPress(playerUseMoney);
			}

			else if (objectList.get(i) instanceof Enemy) {

				if (retreating) {
					((Enemy) objectList.get(i)).setRetreat();

				} else if (attacking) {
					((Enemy) objectList.get(i)).setAttack((PlayableCharacter) objectList.get(0));

				}
				objectList.get(i).move();
			}

			else if (objectList.get(i) instanceof Defender) { // TODO build mode guard and mode wonder in Defender

				if (roaming) {
					((Defender) objectList.get(i)).setRoaming();

				} else if (defending) {
					defender++;
					if (defender % 2 == 0)
						((Defender) objectList.get(i)).setDefending(50 + panelDimensions.width / 3);
					else
						((Defender) objectList.get(i))
								.setDefending(panelDimensions.width - 50 - panelDimensions.width / 3);

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
	//having the sprites check to see if they are colliding with any other sprites 
	private void checkCollisions() { 

		for (int i = 0; i < objectList.size(); i++) {
			if (objectList.get(i) instanceof CollisionSprite) {
				((CollisionSprite) objectList.get(i)).checkCollision(colControl);
			}
		}
	}
	//constructor for the time states to be used for times of day
	private enum TimeState { // TODO this needs to be before JPanel/ before timestate is initialize
		DUSK, DAY, DAWN, NIGHT
	}

	private void timeCalculations() {
		if (timeOfDay > dayLength) {
			timeOfDay = 0;
			days++;
		}
		if (timeOfDay == dayLength * .7 && timeState == timeState.DUSK) {
			timeState = TimeState.NIGHT;
			spawning = true;
			attacking = true;
			backgroundColor = nightColor;

		} else if (timeOfDay == dayLength * .6 && timeState == timeState.DAY) {
			timeState = TimeState.DUSK;
			defending = true;
			backgroundColor = duskColor;

		} else if (timeOfDay == dayLength * .1 && timeState == timeState.DAWN) {
			timeState = TimeState.DAY;
			roaming = true;
			backgroundColor = dayColor;

		} else if (timeOfDay == 0 && timeState == timeState.NIGHT) {
			timeState = TimeState.DAWN;
			retreating = true;
			backgroundColor = dawnColor;
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
		if(e.getKeyCode() == 34)
			playerUseMoney = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 39)
			movingRight = false;
		else if (e.getKeyCode() == 37)
			movingLeft = false;
		if(e.getKeyCode() == 40)
			playerUseMoney = true;
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
	public void mouseClicked(MouseEvent e) { //TODO move to lower level processing (Player)
		Point target = e.getLocationOnScreen();
		Point spawn = objectList.get(0).getPosition();
		System.out.println(objectList.get(0).currentPosition.x + " " + target.x);
		Arrow arrow = new Arrow(spawn.x, spawn.y + (objectList.get(0).getHitBox().height / 2), arrowSprite,
				panelDimensions, (objectList.get(0).currentPosition.x < target.x));
		arrow.moveTo(target.x, 500);
		objectList.add(arrow);

	}

	@Override
	public void mousePressed(MouseEvent e) {

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
	@Override
	public void actionPerformed(ActionEvent e) {
		gameRunning = true;
		startWindowVisible = false;
		parent.requestFocus();
	}

}