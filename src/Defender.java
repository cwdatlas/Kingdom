import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//Programmed by Adrian and Aidan of Carroll college
	
public class Defender extends CollisionSprite implements DefenderI {
	private final Random random;
	private boolean roaming;
	private Rectangle hitBox;
	private Rectangle rangeHitBox;
	private double rangeWidthOfPanel = .3;
	private int arrowCooldownTimer = 0;

	protected Defender(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		
		random = new Random();
		int rangeWidth = (int) (this.img.getWidth() + panelDementions.getWidth()*rangeWidthOfPanel);
		rangeHitBox = new Rectangle(currentPosition.x, currentPosition.y, rangeWidth, this.img.getHeight());
		hitBox = new Rectangle(currentPosition.x, currentPosition.y, this.img.getWidth(), this.img.getHeight());
	}
	@Override
	public void setDefending(int position) {
		roaming = false;
		target.setLocation(position, 500);
	}
	@Override
	public void setRoaming() {
		roaming = true;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(roaming) {
					if(Math.random() < .09) {
						moveTo(random.nextInt((1500-0) + 1), (int) target.getY() );
					}
				}
			}},500, 1000 );
	}
	public void checkCollision(CollisionController colControl) {
		arrowCooldownTimer++;
		List<BaseSprite> collidingSpritesRanged = colControl.checkCollition(rangeHitBox);
		for (int i = 0; i < collidingSpritesRanged.size(); i++) {
			if (collidingSpritesRanged.get(i) instanceof Enemy) {
				if(arrowCooldownTimer >= 1000) {
					arrowCooldownTimer = 0;
					Point targetPoint = new Point(collidingSpritesRanged.get(i).getPosition());
					Arrow arrow = new Arrow(this.getPosition().x, "arrowSprite.png", dimensions, currentPosition.x<targetPoint.x);
					colControl.addObject(arrow);
				}
			}
			}
		List<BaseSprite> collidingSprites = colControl.checkCollition(hitBox);
		for (int i = 0; i < collidingSprites.size(); i++) {
		if (collidingSprites.get(i) instanceof Enemy) {
		colControl.deleteObject(this);
			}
		}
		}
	
	@Override
	public boolean paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.GREEN);
		rangeHitBox.x = (int) (currentPosition.x - (rangeHitBox.width/2) + (img.getWidth()/2));
		hitBox.x = (int) (currentPosition.x - (hitBox.width)+ img.getWidth());
		g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
		g.drawRect(rangeHitBox.x, rangeHitBox.y, rangeHitBox.width, rangeHitBox.height);
		return true;
	}
}


