import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college
public abstract class BaseSprite implements BaseSpriteI {
	protected BufferedImage img;
	protected Point target;
	protected Point currentPosition;
	protected Rectangle hitbox;
	protected boolean blocked = false;
	// build the variables for direction like how Nate built direction in the
	// dolphin program (with worded lists) whatever that was

	// The constructor

	protected BaseSprite(int x, int y, String fileName, Dimension panelDementions) {
		target = new Point(x, y);
		currentPosition = new Point(x, y);
		this.loadImage(fileName);
		
		hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}

	@Override
	public void placeSprite(int x, int y) {
		currentPosition.setLocation(new Point(x, y));	
	}

	@Override
	public void moveTo(int x, int y) {
		target.setLocation(new Point(x, y));
		
	}
	

	@Override
	public boolean move() {
		boolean check = false;
		if(!blocked) {
		try {
			if(target.x > currentPosition.x) {
				currentPosition.setLocation(new Point(currentPosition.x + 1, currentPosition.y));
				hitbox.x = (int) (hitbox.getX()+1);
			}
			else if(target.x < currentPosition.x) {
				currentPosition.setLocation(new Point(currentPosition.x - 1, currentPosition.y));
				hitbox.x = (int) (hitbox.getX()-1);

			}
		}finally{
			// TODO fill this in with the finally catch thing
		}
		}
		return check;
	}

	@Override
	public boolean remove(boolean visability) {
		if(visability == true) {
			visability = false;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean paint(Graphics g) {
		return g.drawImage(img, currentPosition.x, currentPosition.y, null);
	}

	public void loadImage(String fileName) {
		if (fileName != null)
			try {
				img = ImageIO.read(this.getClass().getResource("/images/" + fileName));
			} catch (IOException e) {
				System.out.println("Image not found.");
			}
	}

	
	@Override
	public Point getPosition() {
		return currentPosition;
	}
	public Rectangle getHitBox() {
		return hitbox;
	}

	@Override
	public boolean isColliding(BaseSprite testedSprite) {
		return false;
	}

}
