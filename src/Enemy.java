//Programmed by Adrian and Aidan of Carroll college

public class Enemy extends BaseSprite implements EnemyI{

	protected Enemy(int X, int Y, String fileName) {
		super(X, Y, fileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean setAttack(PlayableCharacter player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setRetreat() {
		// TODO Auto-generated method stub
		return false;
	}

}
