import java.awt.Dimension;

//Programmed by Adrian and Aidan of Carroll college
import java.awt.Point;
/**
 * @author Aidan Scott & Adrien
 * @see Arrow, Defener, Wall and PlayerCharacter in their checkCollitions method to see how Enemy is interacted with
 */
public class Enemy extends BaseSprite implements EnemyI {
	private Point spawnPlace;
	/**
	 * The Constructor sets spawn location for the Enemy
	 * @param x is the x position, top left, of where the sprite will be spawned
	 * @param fileName is the filename of the image that will be used for the sprite
	 * @param panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 */
	protected Enemy(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		spawnPlace = new Point(x, currentPosition.y);
	}
	/**
	 * setAttack() sets the target position of the enemy to the cords of the player
	 * @param player
	 * @see move in BaseSprite to see how target is used to have a sprite move to that location
	 */
	@Override
	public void setAttack(PlayableCharacter player) {
		target = player.getPosition();
	}
	/**
	 * setRetreat() sets the target location to the spawnpoint of the enemies
	 * @see move in BaseSprite to see how target is used to have a sprite move to that location
	 */
	@Override
	public void setRetreat() {
		target = spawnPlace;
	}
	/**
	 * @return spawnPlace location of the enemy sprite
	 */
	public Point getSpawnPlace() {
		return spawnPlace;
	}
}
