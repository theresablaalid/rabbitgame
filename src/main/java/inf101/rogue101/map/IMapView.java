package inf101.rogue101.map;

import java.util.List;

import inf101.grid.Location;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;

public interface IMapView {

	/**
	 * Check if it's legal for an IActor to go into the given location
	 *
	 * @param to A location
	 * @return True if the location isn't already occupied
	 */
	boolean isAvailable(Location loc);

	/**
	 * Get all IActors at the given location
	 * <p>
	 * The returned list either can't be modified, or modifying it won't affect the
	 * map.
	 *
	 * @param loc
	 * @return A list of actors
	 */
	List<IActor> getActors(Location loc);

	/**
	 * Get all items (both IActors and other IItems) at the given location
	 * <p>
	 * The returned list either can't be modified, or modifying it won't affect the
	 * map.
	 *
	 * @param loc
	 * @return A list of items
	 */
	List<IItem> getAll(Location loc);

	/**
	 * Get all non-IActor items at the given location
	 * <p>
	 * The returned list either can't be modified, or modifying it won't affect the
	 * map.
	 *
	 * @param loc
	 * @return A list of items, non of which are instanceof IActor
	 */
	List<IItem> getItems(Location loc);

	/**
	 * @return Height of the map, same as
	 *         {@link #getArea()}.{@link IArea#getHeight()}
	 */
	int getHeight();

	/**
	 * @return Width of the map, same as {@link #getArea()}.{@link IArea#getWidth()}
	 */
	int getWidth();

	/**
	 * Find location of an item
	 *
	 * @param item The item
	 * @return It's location, or <code>null</code> if it's not on the map
	 */
	Location getLocation(IItem item);

	/**
	 * Check if an item exists at a location
	 *
	 * @param loc    The location
	 * @param target The item we're interested in
	 * @return True if target would appear in {@link #getAll(loc)}
	 */
	boolean has(Location loc, IItem target);

	/**
	 * Check for actors.
	 *
	 * @param loc
	 * @return True if {@link #getActors(loc)} would be non-empty
	 */
	boolean hasActors(Location loc);

	/**
	 * Check for non-actors
	 *
	 * @param loc
	 * @return True if {@link #getItem(loc)} would be non-empty
	 */
	boolean hasItems(Location loc);

	/**
	 * Check for walls
	 *
	 * @param loc
	 * @return True if there is a wall at the given location ({@link #getAll(loc)}
	 *         would contain an instance of {@link Wall})
	 */
	boolean hasWall(Location loc);

	/**
	 * Iterate over all grid locations
	 * <p>
	 * See also {@link #iterator()} – using the grid directly in a for-loop will
	 * iterate over the elements.
	 * <p>
	 * All locations obtained through the iterator are guaranteed to be valid
	 * according to {@link #isValid(ILocation)}.
	 *
	 * @return An iterable for iterating over all the locations in the grid
	 */
	public Iterable<Location> locations();
}
