import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college
public class BaseSprite implements BaseSpriteI {
	protected BufferedImage img;
	protected int[] cords;
	protected int[] goToCords;
	// build the variables for direction like how Nate built direction in the
	// dolphin program (with worded lists) whatever that was

	// The constructor
	protected BaseSprite(int X, int Y, String fileName) {
		goToCords = new int[2];
		cords = new int[2];
		cords[0] = X;
		cords[1] = Y;
		this.loadImage(fileName);
		
	}

	@Override
	public boolean place(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveTo(int x, int y) {
		boolean check = false;
		try {
			goToCords[0]=x;
			goToCords[1]=y;
			move();
			check = true;
		}finally {
			// TODO fill this in with the finally catch thing
		}
		return check;
		
	}
	
	public boolean moveTo(int[] Cords) {
		boolean check = false;
		try {
			goToCords[0]=Cords[0];
			goToCords[1]=Cords[1];
			move();
			check = true;
		}finally {
			// TODO fill this in with the finally catch thing
		}
		return check;
	}

	@Override
	public boolean move() {
		boolean check = false;
		try {
			if(goToCords[0]>cords[0]) {
				cords[0]=cords[0]+1;
			}
			else if(goToCords[0]<cords[0]) {
				cords[0]=cords[0]-1;
			}
		}finally{
			// TODO fill this in with the finally catch thing
		}
		return check;
	}

	@Override
	public boolean remove(boolean visability) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean paint(Graphics g) {
		return g.drawImage(img, cords[0], cords[1], null);
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
	public int[] getPosition() {
		// TODO Auto-generated method stub
		return cords;
	}

}
