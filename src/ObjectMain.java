import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Programmed by Adrian and Aidan of Carroll college
public class ObjectMain implements ObjectMain_Interface {
	protected BufferedImage img;
	protected int x;
	protected int y;
	// build the variables for direction like how Nate built direction in the
	// dolphin program (with worded lists) whatever that was

	// The constructor
	protected ObjectMain(int X, int Y) {
		x = X;
		y = Y;

	}

	@Override
	public boolean place(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveTo(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(boolean visability) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean paint(Graphics g) {
		return g.drawImage(img, x, y, null);
	}

	public void loadImage(String fileName) {
		if (fileName != null)
			try {
				img = ImageIO.read(this.getClass().getResource("/images/" + fileName));
			} catch (IOException e) {
				System.out.println("Image not found.");
			}
	}

}
