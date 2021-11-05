import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college
public abstract class BaseSprite implements BaseSpriteI {
	protected BufferedImage img;
	protected Point target;
	protected Point currentPosition;
	protected Dimension dimensions;
	protected Rectangle hitbox;
	protected boolean blockedRight = false;
	protected boolean blockedLeft = false;
	protected boolean visible = true;
	private int gold;

	// build the variables for direction like how Nate built direction in the
	// dolphin program (with worded lists) whatever that was

	// The constructor

	protected BaseSprite(int x, int y, String fileName, Dimension panelDimensions) {
		target = new Point(x, y);
		currentPosition = new Point(x, y);
		dimensions = panelDimensions;
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
		try {
			if(target.x > currentPosition.x && !blockedRight ) {
				currentPosition.setLocation(new Point(currentPosition.x + 1, currentPosition.y));
				hitbox.x = currentPosition.x;
			}
			else if(target.x < currentPosition.x && !blockedLeft) {
				currentPosition.setLocation(new Point(currentPosition.x - 1, currentPosition.y));
				hitbox.x = currentPosition.x;
			}
		}finally{
		}
		
		return check;
	}

	@Override
	public void setvisible(boolean visability) {
			visible = visability;
	}

	@Override
	public boolean paint(Graphics g) {
		if(visible) {
		g.drawImage(img, currentPosition.x, currentPosition.y, null);
		}
		return visible;
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
		// TODO Auto-generated method stub
		return false;
	}
	//the setter for blocking and the direction that the sprite is blocked in
	public void setBlocked(boolean blocking) {
		if(target.x > currentPosition.x && blocking) {
			blockedRight = true;
		}
		else if(blocking) {
			blockedLeft = true;
		}
		else {
			blockedLeft = false;
			blockedRight = false;
		}
	}

	
	@Override
	public int getGold() {
		return gold;
	}

	@Override
	public void setGold(int g) {
		gold = g;	
	}

	@Override
	public int incrementGold() {
		return gold++;
	}
}
