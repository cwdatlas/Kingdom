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

	public static class KingdomPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
		PlayableCharacter player;
		boolean movingRight;
		boolean movingLeft;

		public KingdomPanel() {
			// initialize objects and variables
			player = new PlayableCharacter(40, 40, 0, "playerImage.png");

		}

		public void paintComponent(Graphics g) {
			// Move objects
			
			//sets which direction the player will be moving
			if(movingRight) {
				player.moveRight();
			}
			else if(movingLeft) {
				player.moveLeft();
			}
			else {
				player.stopMoving(); // this could be better if we didnt do this step if player was already still
			}

			// Collision detection and action

			// Painting objects on world panel
			player.paint(g);
			// if (didDraw)
			// System.out.println("drawing was a success");
			// else
			// System.out.println("drawing was a failure");

			// checking to see if game is running, if not end game

			// initialize or remove objects
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==39)
				movingRight = true;
			else if(e.getKeyCode()==37)
				movingLeft = true;
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==39)
				movingRight = false;
			else if(e.getKeyCode()==37)
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