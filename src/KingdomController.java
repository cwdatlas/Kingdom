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
/**
 * @author Aidan Scott & Adrien
 * @see KingdomMain to understand how this class is built and used with the timer to simulate a framerate
 */
public class KingdomController extends JPanel
		implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {
	private boolean gameRunning;
	private boolean spawnedSprites;
	private String enemySprite = "enemySprite.png";
	private String playerSprite = "playerSprite.png";
	private String arrowSprite = "arrowSprite.png";
	private String defenderSprite = "defenderSprite.png";
	private String wallSprite = "wallSprite.png";
	private String defenderShopSprite = "defenderShopSprite.png";
	private String arrowShopSprite = "arrowShopSprite.png";
	private Dimension panelDimensions;
	private boolean movingRight;
	private boolean movingLeft;
	private boolean playerUseMoney;
	private boolean playerShootingArrow;
	private int arrowTarget;
	// time variables
	private int days = 0;
	private int timeOfDay = 0;
	private TimeState timeState = TimeState.DAWN;
	private Difficulty difficulty = Difficulty.EASY;
	private ShopType shopType;
	private boolean spawning = false;
	private boolean defending = false;
	private boolean roaming = true;
	private boolean attacking = false;
	private boolean retreating = false;
	private Color dawnColor = new Color(255, 153, 51, 30);
	private Color dayColor = new Color(6, 6, 6, 0);
	private Color duskColor = new Color(255, 153, 51, 30);
	private Color nightColor = new Color(0, 0, 0, 120);
	private Color bloodMoonColor = new Color(255, 0, 0, 60);
	private Color backgroundColor = new Color(128, 255, 191, 30);
	private BufferedImage backgroundImage;

	private int dayLength = 5000; // a full day at 5 min day should be around 30,000 frames
	private int defenders;
	private int walls;
	private int players;
	private int defendersSpawned;
	private int playerGold;
	private CollisionController colControl;

	private final Random random;
	private JFrame parent;
	String instructionsText = "Welcome to Kingdom! The point of this game is to survive as many nights as possible"
			+ "from the ENEMIES(the green rectangles). In order to help you survive, EMEMIES will drop GOLD in"
			+"order for you to buy precious resources such as ARROWS, DEFENDERS, and to rebuild WALLS. To shoot,"
			+"place the mouse cursor to the left or right of your character and left click to shoot in the chosen"
			+"direction. To buy ARROWS, go to the maroon arrow area and press the 'DOWN' directional key. To buy"
			+"DEFENDERS, go to the DEFENDER box and press the 'DOWN' directional key to buy. To rebuild WALLS, go"
			+"to a wall and press the 'DOWN' directional key to rebuild the wall. Note: completley broken walls"
			+"will be grey with a red highlight(ENEMIES will pass through them).";
	private JLabel instructions = new JLabel("<html>"+instructionsText+"</html>");
//	private JLabel instructions = new JLabel();
	private boolean instructionsVisable = true;
	private JPanel startWindow = new JPanel();
	private boolean startWindowVisible = true;
	private JButton startGame = new JButton();
	private JPanel difficultyWindow = new JPanel();
	private boolean difficultyWindowVisible = true;
	private JPanel youDiedWindow = new JPanel();
	private boolean youDiedVisible = false;
	private JButton playAgain = new JButton();
	
	//makes difficulty buttons
	private JButton easyBtn = new JButton();
	private JButton mediumBtn = new JButton();
	private JButton hardBtn = new JButton();
	private JButton crazyBtn = new JButton();

	private JLabel tod = new JLabel(); // this shows the time of day as a sting under coinPanel
	private JLabel coinPanel = new JLabel(); // this sets up the coins or score board
	// display of amounts at end of game
	private JLabel enemiesKilled = new JLabel();
	private JLabel endGold = new JLabel();
	private JLabel livingDefenders = new JLabel();
	private JLabel totalScore = new JLabel();
	private JLabel daysPassed = new JLabel();
	private JLabel playerArrows = new JLabel();
	private List<BaseSprite> objectList;
	/**
	 * KingdomController constructor initializes graphic components like JLabels
	 * it also initializes many variables 
	 * @param parentPanel the JFrame that KingdomController is inside of
	 */
	public KingdomController(JFrame parentPanel) {
		setVariables(); //(READ) sets the variables that will need to be reset when the user wants to play
				
		parent = parentPanel;
		this.random = new Random();
		panelDimensions = new Dimension();
		this.add(coinPanel);
		this.add(tod);
		this.add(playerArrows);
		try {
			backgroundImage = ImageIO.read(this.getClass().getResource("/images/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Panel parameters
		instructions.setVisible(true);
		// startWindow params
		startWindow.setBackground(new Color(200, 150, 0, 200));
		TitledBorder log2;
		log2 = BorderFactory.createTitledBorder("Start Game");
		log2.setTitleColor(Color.WHITE);
		log2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		startWindow.add(instructions);
		startWindow.setBorder(log2);
		startWindow.setLayout(null);
		this.add(startWindow);
		
		//difficultyWindow
		difficultyWindow.setBackground(new Color(200, 150, 200, 200));
		TitledBorder diff;
		diff = BorderFactory.createTitledBorder("Difficulty Window");
		diff.setTitleColor(Color.WHITE);
		diff.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		difficultyWindow.setBorder(diff);
		difficultyWindow.setLayout(null);
		this.add(difficultyWindow);

		// youDiedWindow
		youDiedWindow.setBackground(Color.BLACK);
		TitledBorder log3;
		log3 = BorderFactory.createTitledBorder("You Died");
		log3.setTitleColor(Color.WHITE);
		log3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		youDiedWindow.setBorder(log3);
		youDiedWindow.setLayout(null);
		this.add(youDiedWindow);

		// buttons
		startGame.setText("START");
		startGame.setBackground(Color.WHITE);
		startGame.setVisible(true);
		startGame.addActionListener(this);
		startWindow.add(startGame);
		
		easyBtn.setText("EASY");
		easyBtn.setBackground(Color.WHITE);
		easyBtn.setVisible(true);
		easyBtn.addActionListener(this);
		difficultyWindow.add(easyBtn);
		
		mediumBtn.setText("MEDIUM");
		mediumBtn.setBackground(Color.WHITE);
		mediumBtn.setVisible(true);
		mediumBtn.addActionListener(this);
		difficultyWindow.add(mediumBtn);

		hardBtn.setText("HARD");
		hardBtn.setBackground(Color.WHITE);
		hardBtn.setVisible(true);
		hardBtn.addActionListener(this);
		difficultyWindow.add(hardBtn);
		
		crazyBtn.setText("CARZY");
		crazyBtn.setBackground(Color.WHITE);
		crazyBtn.setVisible(true);
		crazyBtn.addActionListener(this);
		difficultyWindow.add(crazyBtn);
		
		playAgain.setText("Play Again");
		playAgain.setBackground(Color.WHITE);
		playAgain.setVisible(true);
		playAgain.addActionListener(this);
		youDiedWindow.add(playAgain);

		// JLabels
		enemiesKilled.setForeground(Color.WHITE);
		endGold.setForeground(Color.WHITE);
		livingDefenders.setForeground(Color.WHITE);
		totalScore.setForeground(Color.WHITE);
		daysPassed.setForeground(Color.WHITE);
		youDiedWindow.add(enemiesKilled);
		youDiedWindow.add(endGold);
		youDiedWindow.add(livingDefenders);
		youDiedWindow.add(totalScore);
		youDiedWindow.add(daysPassed);
	}
	/**
	 * PaintComponent() starts all technical proccesssing like time, setting bounds dependent on panel size and sprite movement, collision checking
	 * and other general actions
	 * @param g
	 */
	public void paintComponent(Graphics g) {
		panelDimensions = this.getSize();

		g.drawImage(backgroundImage, 0, 0, panelDimensions.width, panelDimensions.height, null);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, panelDimensions.width, panelDimensions.height);

		startWindow.setBounds(panelDimensions.width / 3, (int) (panelDimensions.height * .1), panelDimensions.width / 3,
				(int) (panelDimensions.height * .7));
		startWindow.setVisible(startWindowVisible);
		instructions.setBounds((int) (startWindow.getWidth() * .01), 80, (int) (startWindow.getWidth()), (int) (startWindow.getHeight() * .85));
		startGame.setBounds((int) (startWindow.getWidth() * .3), (int) (startWindow.getHeight() * .8),
				(int) (startWindow.getWidth() * .4), (int) (startWindow.getHeight() * .1));
		difficultyWindow.setBounds(panelDimensions.width / 3, (int) (panelDimensions.height * .1), panelDimensions.width / 3,
				(int) (panelDimensions.height * .7));
		difficultyWindow.setVisible(difficultyWindowVisible);
		easyBtn.setBounds((int) (difficultyWindow.getWidth() * .1), (int) (difficultyWindow.getHeight() * .2),
				(int) (difficultyWindow.getWidth() * .2), (int) (difficultyWindow.getHeight() * .1));
		mediumBtn.setBounds((int) (difficultyWindow.getWidth() * .7), (int) (difficultyWindow.getHeight() * .2),
				(int) (difficultyWindow.getWidth() * .2), (int) (difficultyWindow.getHeight() * .1));
		hardBtn.setBounds((int) (difficultyWindow.getWidth() * .1), (int) (difficultyWindow.getHeight() * .8),
				(int) (difficultyWindow.getWidth() * .2), (int) (difficultyWindow.getHeight() * .1));
		crazyBtn.setBounds((int) (difficultyWindow.getWidth() * .7), (int) (difficultyWindow.getHeight() * .8),
				(int) (difficultyWindow.getWidth() * .2), (int) (difficultyWindow.getHeight() * .1));
		youDiedWindow.setBounds(panelDimensions.width / 3, (int) (panelDimensions.height * .1),
				panelDimensions.width / 3, (int) (panelDimensions.height * .7));
		
		
		
		playAgain.setBounds((int) (youDiedWindow.getWidth() * .3), (int) (youDiedWindow.getHeight() * .8),
				(int) (youDiedWindow.getWidth() * .4), (int) (youDiedWindow.getHeight() * .1));
		enemiesKilled.setBounds((int) (youDiedWindow.getWidth() * .01), 20, (int) (youDiedWindow.getWidth() * .4), 20);
		endGold.setBounds((int) (youDiedWindow.getWidth() * .01), 40, (int) (youDiedWindow.getWidth() * .4), 20);
		livingDefenders.setBounds((int) (youDiedWindow.getWidth() * .01), 60, (int) (youDiedWindow.getWidth() * .4),
				20);
		daysPassed.setBounds((int) (youDiedWindow.getWidth() * .01), 80, (int) (youDiedWindow.getWidth() * .4), 20);
		totalScore.setBounds((int) (youDiedWindow.getWidth() * .01), 100, (int) (youDiedWindow.getWidth() * .4), 20);
		youDiedWindow.setVisible(youDiedVisible);

		if (!gameRunning && !spawnedSprites) { // spawn things that are dependent on frame width
			spawnPlayers(players);
			spawnDefenders(defenders);
			spawnWalls(walls);
			spawnShop(ShopType.arrowType);
			spawnShop(ShopType.defenderType);
			spawnedSprites = true;
		}
		if (!(objectList.get(0) instanceof PlayableCharacter)) { // this is a sign that the player died
			enemiesKilled.setText("Enemies Killed: " + colControl.enemiesKilled);
			endGold.setText("Ending Gold: " + playerGold);
			int livDefenders = defendersSpawned - colControl.defendersKilled;
			livingDefenders.setText("total living Defenders: " + livDefenders);
			daysPassed.setText("days Passed: " + days);
			int score = days * (colControl.enemiesKilled - colControl.defendersKilled) + playerGold;
			totalScore.setText("Score: " + score);
			youDiedVisible = true;
			gameRunning = false;
			setVariables();
		}
		if (panelDimensions != null && gameRunning) { // then this runs the game
			doMoves();
			checkCollisions();
			for (int i = 0; i < objectList.size(); i++) { // Painting first layer of Objects
				if (objectList.get(i) instanceof Wall || objectList.get(i) instanceof Shop) {
					objectList.get(i).paint(g);
				}
			}

			for (int i = 0; i < objectList.size(); i++) { // Painting second layer
				if (objectList.get(i) instanceof PlayableCharacter) {
					objectList.get(i).paint(g);
				}
			}
			for (int i = 0; i < objectList.size(); i++) { // Painting third layer
				if (objectList.get(i) instanceof Arrow || objectList.get(i) instanceof Defender
						|| objectList.get(i) instanceof DroppedCoin || objectList.get(i) instanceof Enemy) {
					objectList.get(i).paint(g);
				}
			}

			timeCalculations();
			if (spawning) {
				spawning = false;
				if(timeState == TimeState.BLOODMOON) {
					spawnEnemies((4 + days) * 4);
				}else {
					spawnEnemies((4 + days) * 2);
				}
			}
		}
		// JLabels
		// this sets up the coins or score board
		playerGold = objectList.get(0).getGold();
		coinPanel.setBounds((int)panelDimensions.width - 150,0,100,100);
		tod.setBounds((int)panelDimensions.width - 150,10,200,110);
		playerArrows.setBounds((int)panelDimensions.width - 150,20,300,120);
		coinPanel.setText("Gold: " + playerGold);
		tod.setText("Time of Day: " + timeState);
		coinPanel.setForeground(Color.WHITE);
		tod.setForeground(Color.WHITE);
		playerArrows.setForeground(Color.WHITE);
	}
	/**
	 * spawnPlayers() initialize PlayerCharacter sprites at a specific location
	 * @param numberOfPlayers
	 */
	private void spawnPlayers(int numberOfPlayers) {
		for (int d = 0; d < numberOfPlayers; d++)
			objectList.add(0,
					new PlayableCharacter(((int) panelDimensions.getWidth() / 2), 10, 4, playerSprite, panelDimensions));
	}
	/**
	 * spawnDefenders() initializes Defenders in a single location
	 * @param numberOfDefenders
	 */
	private void spawnDefenders(int numberOfDefenders) { // TODO set spawn parameters (place)
		for (int d = 0; d < numberOfDefenders; d++) {
			objectList.add(new Defender(800, defenderSprite, panelDimensions));
			defendersSpawned++;
		}
	}
	/**
	 * spawnEnemies() initializes a specific quantity of enemies at either side of the map past the bounds of the panel
	 * @param numberOfEnemies
	 */
	private void spawnEnemies(int numberOfEnemies) {// TODO set spawn parameters (place)
		for (int d = 0; d < numberOfEnemies; d++) {
			if (d % 2 == 0)
				objectList.add(new Enemy(random.nextInt(300) - 500, enemySprite, panelDimensions));
			else
				objectList.add(new Enemy(random.nextInt(300) + (int) panelDimensions.getWidth(), enemySprite,
						panelDimensions));
		}
	}
	/**
	 * spawnWalls() initializes walls at two set locations, usually two walls or less will be initialised
	 * @param numberOfWalls
	 */
	private void spawnWalls(int numberOfWalls) {// TODO set spawn parameters (place)
		for (int d = 0; d < numberOfWalls; d++) {
			if (d % 2 == 0)
				objectList.add(new Wall(((int) panelDimensions.getWidth() / 3), wallSprite, panelDimensions));
			else
				objectList.add(new Wall(((int) panelDimensions.getWidth() / 3) * 2, wallSprite, panelDimensions));
		}
	}
	/**
	 * spawnShop() initialized a shop at a specific location depending on the shop type
	 * @param type
	 * @see the class ShopType to find out the shop types and add more 
	 */
	private void spawnShop(ShopType type) {
		if(type == ShopType.defenderType)
			objectList.add(new Shop(((int) panelDimensions.getWidth() / 2) + 50, defenderShopSprite, panelDimensions, type));
		if(type == ShopType.arrowType)
			objectList.add(new Shop(((int) panelDimensions.getWidth() / 2) - 130, arrowShopSprite, panelDimensions, type));
	}
	/**
	 * doMoves() deals with all movement of sprites, that being how they are supposed to move or if they should move at all
	 * When the sprites have a location to go to the move() function needs to be called for them to go twards that target location
	 */
	private void doMoves() {
		int defender = 0;
		for (int i = 0; i < objectList.size(); i++) {
			if (objectList.get(i) instanceof PlayableCharacter) { // more efficient way of doing this class //
																	// comparison
				if (movingRight) {
					((PlayableCharacter) objectList.get(i)).moveRight();// telling the playable character each frame
																		// to keep moving if true
				} else if (movingLeft) {
					((PlayableCharacter) objectList.get(i)).moveLeft();
				}
				((PlayableCharacter) objectList.get(i)).downPress(playerUseMoney);
				((PlayableCharacter) objectList.get(i)).playerShoot(playerShootingArrow, arrowTarget);
				playerShootingArrow = false;
				playerArrows.setText("Arrows: " + ((PlayableCharacter)objectList.get(i)).getArrows());
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
	/**
	 * checkCollisions() loops through the entire BaseSprite list and asks the sprites to checkCollision for only the sprites that can
	 * @see CollisionController to understand how collition works
	 */
	private void checkCollisions() {

		for (int i = 0; i < objectList.size(); i++) {
			if (objectList.get(i) instanceof CollisionSprite) {
				((CollisionSprite) objectList.get(i)).checkCollision(colControl);
			}
		}
	}
	/**
	 * TimerState is a enum for the times of the day
	 * @see timeCalculations() to see how the class is used
	 */
	private enum TimeState { // TODO this needs to be before JPanel/ before timestate is initialize
		DUSK, DAY, DAWN, NIGHT, BLOODMOON
	}
	/**
	 * Difficulty is the set difficulty of the game
	 * @see setVariables() to see how the class is being used
	 */
	private enum Difficulty{
		EASY, MEDIUM, HARD, CRAZY
	}
	/**
	 * timeCalculations() regulates the time of the day dependent on the amount of frames that have been recorded
	 * depending on the time of day it is timeCalculations will set different events to occur 
	 * @see TimeState to see how the times are stored
	 */
	private void timeCalculations() {
		if (timeOfDay > dayLength) {
			timeOfDay = 0;
			days++;
		}
		if (timeOfDay == dayLength * .7 && timeState == timeState.DUSK) {
			spawning = true;
			attacking = true;
			backgroundColor = nightColor;
			if(Math.random()>.9) {
				backgroundColor = bloodMoonColor;
				timeState = TimeState.BLOODMOON;
			}
			timeState = TimeState.NIGHT;
		} else if (timeOfDay == dayLength * .6 && timeState == timeState.DAY) {
			timeState = TimeState.DUSK;
			defending = true;
			backgroundColor = duskColor;

		} else if (timeOfDay == dayLength * .1 && timeState == timeState.DAWN) {
			timeState = TimeState.DAY;
			roaming = true;
			backgroundColor = dayColor;

		} else if (timeOfDay == 0 && (timeState == TimeState.NIGHT || timeState == TimeState.BLOODMOON)) {
			timeState = TimeState.DAWN;
			retreating = true;
			backgroundColor = dawnColor;
		}
		timeOfDay++;
	}
	/**
	 * setVariables() resets the core variables that need to be loaded at the start of the game and when the game needs to be reset
	 * different choices in setVarables change difficulty of the game
	 * @see the enum class Difficulty to see all the dificulties
	 */
	private void setVariables() {
		players = 1;
		days = 0;
		defendersSpawned = 0;
		spawnedSprites = false;
		gameRunning = false;
		objectList = new ArrayList();
		colControl = new CollisionController(objectList);
		walls = 2;		
		if(difficulty == Difficulty.EASY) {
		defenders = 2;
		walls = 2;
		playerGold = 10;
		timeOfDay = 0;
		timeState = TimeState.DAWN;
	  } else if(difficulty == Difficulty.MEDIUM) {
		defenders = 1;
		walls = 2;
		playerGold = 10;
		timeOfDay = 0;
		timeState = TimeState.DAWN;
	  } else if (difficulty == Difficulty.HARD) {
		defenders = 0;
		walls = 2;
		playerGold = 4;
		timeOfDay = 0;
		timeState = TimeState.DAWN;
	  } else if (difficulty == Difficulty.CRAZY) {
		defenders = 0;
		walls = 2;
		playerGold = 0;
		timeOfDay = 0;
		timeState = TimeState.DAWN;
	}
}

	// key and mouse listener events
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * KeyPressed() is built off the the key listener that KingdomController implements
	 * this class deals with the key press which sets booleans to true
	 * @param e 
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 39) {
			movingRight = true;
		}
		else if (e.getKeyCode() == 37)
			movingLeft = true;
		if (e.getKeyCode() == 40)
			playerUseMoney = true;
	}
	/**
	 * KeyReleased() is built off the the key listener that KingdomController implements
	 * this class deals with the key release which sets booleans to false
	 * @param e 
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 39)
			movingRight = false;
		else if (e.getKeyCode() == 37)
			movingLeft = false;
		if (e.getKeyCode() == 40)
			playerUseMoney = false;
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
	/**
	 * mouseClicked() is built off the the mouse listener that KingdomController implements
	 * this function gets called when a mouse was clicked on KingdomController
	 * @param e 
	 */
	@Override
	public void mouseClicked(MouseEvent e) { // TODO move to lower level processing (Player)
		Point targetPoint = e.getLocationOnScreen();
		arrowTarget = targetPoint.x;
		playerShootingArrow = true;
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
	/**
	 * actionPerformed() is called when a button that has been added to KingdomController is clicked
	 * @param e 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startGame) {
			startWindowVisible = false;
		} else if (e.getSource() == playAgain) {
			youDiedVisible = false;
			parent.requestFocus();
			gameRunning = true;
		}
		else if (e.getSource() == easyBtn) {
			gameRunning = true;
			difficulty = Difficulty.EASY;
			difficultyWindowVisible = false;
			parent.requestFocus();
		}
		else if (e.getSource() == mediumBtn) {
			gameRunning = true;
			difficulty = Difficulty.MEDIUM;
			difficultyWindowVisible = false;
			parent.requestFocus();
		}
		else if (e.getSource() == hardBtn) {
			gameRunning = true;
			difficulty = Difficulty.HARD;
			difficultyWindowVisible = false;
			parent.requestFocus();
		}
		else if (e.getSource() == crazyBtn) {
			gameRunning = true;
			difficulty = Difficulty.CRAZY;
			difficultyWindowVisible = false;
			parent.requestFocus();
		}
	}
}
/**
 * ShopType is an enum class that sets the type of shop a shopSprite turns into
 */
enum ShopType { // this needs to be available for the shop class to use and for any other class
	// that it is being handed to
defenderType, arrowType
}