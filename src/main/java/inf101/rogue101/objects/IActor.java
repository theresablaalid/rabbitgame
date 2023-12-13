package inf101.rogue101.objects;

import inf101.rogue101.game.IGameView;

/**
 * An actor is an IItem that can also do something, either controlled by the
 * computer (INonPlayer) or the user (IPlayer).
 *
 * @author anya
 *
 */
public interface IActor extends IItem {
	/**
	 * @return This actor's attack score (used against an item's
	 *         {@link #getDefence()} score to see if an attack is successful)
	 */
	int getAttack();

	/**
	 * @return The damage this actor deals on a successful attack (used together
	 *         with
	 *         {@link #handleDamage(int)} on
	 *         the target)
	 */
	int getDamage();

	/**
	* Do one turn
	* Most items will do nothing. Subtypes may override with their own behavior. 
	*
	* @param game Game, for interacting with the world
	*/

	void doTurn(IGameView game);
}
