package inf101.rogue101.game;

import java.util.List;
import java.util.Optional;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.map.IGameMap;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.IPlayer;

/**
 * Game interface
 * <p>
 * The game has a map and a current {@link IActor} (the player or non-player
 * whose turn it currently is). The game also knows the current location of the
 * actor. Most methods that deal with the map will use this current location –
 * they are meant to be used by the current actor for exploring or interacting
 * with its surroundings.
 * <p>
 * In other words, you should avoid calling most of these methods if you're not
 * the current actor. You know you're the current actor when you're inside your
 * {@link IPlayer#keyPressed()} or {@link INonPlayer#doTurn()} method.
 *
 * @author anya, Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public interface IGame{

	IMessageView getIMessageView();
	
	/**
	 * Add an item to the given location
	 * <p>
	 * If the item is an actor, it won't move until the next turn.
	 *
	 * @param item
	 * @return true if the item was added to the game, false otherwise
	 */
	boolean addItem(IItem item, Location loc);
	
	/**
	 * Removes an item from the given location
	 * <p>
	 * If some item equal to item is found on that location.
	 * The item found will be returned,
	 * otherwise null is returned.
	 *
	 * @param item
	 */
	Optional<IItem> removeItem(IItem item, Location loc);

	/**
	 * @return The height of the map
	 */
	int getHeight();

	/**
	 * @return Width of the map
	 */
	int getWidth();

	/**
	 * Get the current actor's location.
	 * <p>
	 * You should only call this from an IActor that is currently doing its move.
	 *
	 * @return Location of the current actor
	 */
	Location getCurrentLocation();

	/**
	 * Get the current actor
	 * <p>
	 * You can check if it's your move by doing game.getActor()==this.
	 *
	 * @return The current actor (i.e., the (IPlayer/INonPlayer) player who's turn
	 *         it currently is)
	 */
	IActor getCurrentActor();

	/**
	 * @return The map
	 */
	IGameMap getMap();

	/**
	 * Get a list of all locations that are visible from the current location.
	 * <p>
	 * The location list is sorted so that nearby locations come earlier in the
	 * list. E.g., if <code>l = getVisible()<code> and <code>i < j</code>then
	 * <code>getLocation().gridDistanceTo(l.get(i)) < getLocation().gridDistanceTo(l.get(j))</code>
	 *
	 * @return A list of grid cells visible from the {@link #getLocation()}
	 */
	List<Location> getReachable();

	/**
	 * @return A list of directions we can move in, for use with
	 *         {@link #move(GridDirection)}
	 */
	List<GridDirection> getPossibleMoves(IActor actor);

	/**
	 * Move the current actor in the given direction.
	 * <p>
	 * The new location will be returned.
	 *
	 * @param dir
	 * @return A new location
	 * @throws IllegalMoveException if moving in that direction is illegal
	 */
	Location move(IActor actor, GridDirection dir);
	
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
	public boolean attack(IActor attacker, GridDirection dir);
	
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
	Location attack(IActor attacker, GridDirection dir, IItem target) throws IllegalMoveException;

	/**
	 * Perform a ranged attack on the target.
	 * <p>
	 * Rules for this are up to you!
	 *
	 * @param dir    Direction
	 * @param target A target item, which should in some square in the given
	 *               direction
	 * @return true if the attack resulted in killing an enemy
	 */
	boolean rangedAttack(IActor attacker, GridDirection dir, IItem target);
	
	/**
	 * @param dir
	 * @return True if it's possible to move in the given direction
	 */
	boolean canGo(IActor actor, GridDirection dir);
	
	/**
	 * Checks if a location contains an item of a specific class
	 * @param <T> class of the item
	 * @param loc the location
	 * @param c class of the item
	 * @return true if location contains an item of that class 
	 */
	<T extends IItem> boolean containsItem(Location loc, Class<T> c);

	Optional<IItem> pickUpItem(IActor currentActor, IItem item);

}
