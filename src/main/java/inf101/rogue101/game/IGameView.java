package inf101.rogue101.game;

import java.util.List; 
import java.util.Optional;

import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;

/**
 * Actors in the game does not have complete information about the map.
 * They only see what is close by.
 * This interface provides the methods that IActors may use to do their move.
 * 
 * The game has rules for where a player is allowed to go, these rules
 * are enforced by methods in this interface.
 * 
 * One of the points with this Interface is to limit the IActor from knowing its position
 * They only see their surroundings. 
 * (Unless they have found a GPS or Harry Potters magic map)
 * 
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public interface IGameView extends IMessageView{

	/**
	 * Perform an attack by the current {@link IActor} in the provided direction
	 * If the location in the direction is occupied by another {@link IActor}, 
	 * the attack is performed similarly to {@link attack(GridDirection, IItem)}.
	 * 
	 * @param dir the direction to attack in 
	 * @return true if attack succeeds, false otherwise
	 */
	public boolean attack(GridDirection dir);
	
	/**
	 * Perform an attack by the current {@link IActor} on the provided target and
	 * moves into the target's location if successful
	 * <p>
	 * Will compare the attacker's attack score {@link IActor#getAttack()} against
	 * the target's defence score {@link IItem#getDefence()} to determine if the
	 * attack succeeds;
	 * 
	 * 
	 * If an attack succeeds, the target is dealt damage {@link IActor#getDamage()}
	 * using the method {@link IItem#handleDamage(int)} and the
	 * attacker is moved in the provided direction.
	 *
	 * @param dir    The direction the attacker will move in, such the the target is
	 *               found there
	 * @param target A target item, which must be found in the provided direction
	 * @throws IllegalMoveException if the direction indicates an illegal move
	 * 
	 * @return the attacker's new location, or the previous location if the attack
	 *         failed
	 */
	boolean attack(GridDirection dir, IItem target) throws IllegalMoveException;

	/**
	 * @param dir
	 * @return True if it's possible to move in the given direction
	 */
	boolean canGo(GridDirection dir);


	/**
	 * Pick up an item
	 * <p>
	 * This should be used by IActors who want to pick up an item and carry it. The
	 * item will be returned if picking it succeeded (the actor <em>might</em> also
	 * make a mistake and pick up the wrong item!).
	 *
	 * @param item An item, should be in the current map location
	 * @return The item that was picked up (normally <code>item</code>), or
	 *         <code>null</code> if it failed
	 */
	Optional<IItem> pickUp(IItem item);

	/**
	 * Drop an item
	 * <p>
	 * This should be used by IActors who are carrying an item and want to put it on
	 * the ground. Check the return value to see if it succeeded.
	 *
	 * @param item An item, should be carried by the current actor and not already
	 *             be on the map
	 * @return True if the item was placed on the map, false means you're still
	 *         holding it
	 */
	boolean drop(IItem item);

	/**
	 * Possible moves are the moves an IActor can make.
	 * Note that this does not include the CENTER direction.
	 * A player could choose to move in the CENTER direction but it would
	 * not result in an actual move.
	 * 
	 * @return A list of directions we can move in, for use with
	 *         {@link #move(GridDirection)}
	 */
	List<GridDirection> getPossibleMoves();

	/**
	 * Move the current actor in the given direction.
	 * <p>
	 * The new location will be returned.
	 *
	 * @param dir
	 * @return A new location
	 * @throws IllegalMoveException if moving in that direction is illegal
	 */
	boolean move(GridDirection dir);

	/**
	 * Perform a ranged attack on the target.
	 * <p>
	 * Rules for this are up to you!
	 *
	 * @param dir    Direction
	 * @param target A target item, which should in some square in the given
	 *               direction
	 * @return Your new location if the attack resulted in you moving (unlikely)
	 */
	boolean rangedAttack(GridDirection dir, IItem target);

	/**
	 * IActors can see the nearby cells and what is on them.
	 * This method checks if a neighbour location contains an item of a specific class
	 * 
	 * Although it may seem unlogical that an actor can see a small key 
	 * hidden under a large rock from a distance, this is just how the game works.
	 * 
	 * usage:
	 *     boolean found = game.containsItem(dir, Gold.class)
	 *     
	 * @param <T> class of the item
	 * @param loc the location
	 * @param c class of the item
	 * @return true if location contains an item of that class 
	 */
	<T extends IItem> boolean containsItem(GridDirection dir, Class<T> c);

	/**
	 * @return A list of the non-actor items at the current map location
	 */
	List<IItem> getLocalNonActorItems();

	/**
	 * Get all locations within i steps from the current location
	 * 
	 * @param dist
	 * @return A list of locations, all at most i grid cells away from centre
	 */
	List<IItem> getNearbyItems(int dist);
	
	/**
	 * Get direction towards an visible object, only close by objects will be visible
	 * @param item The item you want to look for
	 * @return The direction you should go
	 */
	GridDirection getDirectionTo(IItem item);
}
