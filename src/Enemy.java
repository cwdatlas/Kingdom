import java.awt.Dimension;

//Programmed by Adrian and Aidan of Carroll college
import java.awt.Point;



public class Enemy extends BaseSprite implements EnemyI {
	private Point spawnPlace;

	protected Enemy(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		spawnPlace = new Point(x, currentPosition.y);
	}

	@Override
	public void setAttack(PlayableCharacter player) {
		target = player.getPosition();
	}

	@Override
	public void setRetreat() {
		target = spawnPlace;

	}
	public Point getSpawnPlace() {
		return spawnPlace;
	}
}
