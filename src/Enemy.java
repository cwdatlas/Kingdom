import java.awt.Dimension;

//Programmed by Adrian and Aidan of Carroll college
import java.awt.Point;
/**
 * @author Aidan Scott & Adrien
 * Enemy is used to Sprite to interact with, it can attack the player, and retreat to their spawn positions
 * @see Arrow, Defener, Wall and PlayerCharacter in their checkCollitions method to see how Enemy is interacted with
 */
public class Enemy extends BaseSprite implements EnemyI {
	private Point spawnPlace;
	/**
	 * The Constructor sets spawn location for the Enemy
	 * @param int x is the x position, top left, of where the sprite will be spawned
	 * @param String fileName is the filename of the image that will be used for the sprite
	 * @param Dimension panelDementions are the dimensions for the panel being used so the arrow can be placed at the correct y value
	 */
	protected Enemy(int x, String fileName, Dimension panelDementions) {
		super(x, fileName, panelDementions);
		spawnPlace = new Point(x, currentPosition.y);
	}
	/**
	 * setAttack sets the target position of the enemy to the cords of the player, making the enemy follow the player
	 * @param PlayableCharacter player
	 * @see move in BaseSprite to see how target is used to have a sprite move to that location
	 */
	@Override
	public void setAttack(PlayableCharacter player) {
		target = player.getPosition();
	}
	/**
	 * setRetreat sets the target location to the spawnpoint of the enemies
	 * @see move in BaseSprite to see how target is used to have a sprite move to that location
	 */
	@Override
	public void setRetreat() {
		target = spawnPlace;
	}
	/**
	 * @return spawn location of the enemy sprite
	 */
	public Point getSpawnPlace() {
		return spawnPlace;
	}
}
