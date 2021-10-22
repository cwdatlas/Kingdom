import java.awt.Dimension;

//Programmed by Adrian and Aidan of Carroll college

public class Enemy extends BaseSprite implements EnemyI {
	int[] spawnPlace;

	protected Enemy(int X, int Y, String fileName, Dimension panelDementions) {
		super(X, Y, fileName, panelDementions);
		spawnPlace = new int[2];
		spawnPlace[0] = X;
		spawnPlace[1] = Y;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAttack(PlayableCharacter player) {
		goToCords = player.getPosition();
	}

	@Override
	public void setRetreat() {
		goToCords = spawnPlace;

	}

}
