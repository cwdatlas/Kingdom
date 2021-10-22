//Programmed by Adrian and Aidan of Carroll college
import java.awt.Point;



public class Enemy extends BaseSprite implements EnemyI {
	Point spawnPlace;

	protected Enemy(int x, int y, String fileName) {
		super(x, y, fileName);
		spawnPlace = new Point(x, y);
//		spawnPlace = new int[2];
//		spawnPlace[0] = X;
//		spawnPlace[1] = Y;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAttack(PlayableCharacter player) {
		target = player.getPosition();
	}

	@Override
	public void setRetreat() {
		target = spawnPlace;

	}

}
