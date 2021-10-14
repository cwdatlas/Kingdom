//Programmed by Adrian and Aidan of Carroll college

public class Enemy extends BaseSprite implements EnemyI {
	int[] spawnPlace;

	protected Enemy(int X, int Y, String fileName) {
		super(X, Y, fileName);
		spawnPlace[0] = X;
		spawnPlace[1] = Y;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAttack(PlayableCharacter player) {
		goToCords = player.getPosition();
	}

	@Override
	public boolean setRetreat() {
		boolean check = false;
		try {
			goToCords = spawnPlace;
			check = true;
		} finally {

		}
		goToCords = spawnPlace;
		return check;
	}

}
