package inf101.grid;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IMultiGrid<T> extends IGrid<List<T>> {

	/**
	 * Add to the cell at the given location.
	 *
	 * @param loc     The (x,y) position of the grid cell to get the contents of.
	 * @param element An element to be added to the cell.
	 * @throws IndexOutOfBoundsException if !isValid(loc)
	 */
	default void add(Location loc, T element) {
		get(loc).add(element);
	}

	/**
	 * Check if a cell contains an element.
	 *
	 *
	 * @param loc       The (x,y) position of the grid cell
	 * @param predicate Search predicate.
	 * @return true if an element matching the predicate was found
	 * @throws IndexOutOfBoundsException if !isValid(loc)
	 */
	default boolean contains(Location loc, Predicate<T> predicate) {
		for (T t : get(loc)) {
			if (predicate.test(t))
				return true;
		}
		return false;
	}

	/**
	 * Check if a cell contains an element.
	 *
	 *
	 * @param loc     The (x,y) position of the grid cell
	 * @param element An element to search for.
	 * @return true if element is at the given location
	 * @throws IndexOutOfBoundsException if !isValid(loc)
	 */
	default boolean contains(Location loc, T element) {
		return get(loc).contains(element);
	}


	/**
	 * Get all elements in a cell that match the predicate
	 *
	 *
	 * @param loc       The (x,y) position of the grid cell
	 * @param predicate Search predicate.
	 * @return true if an element matching the predicate was found
	 * @throws IndexOutOfBoundsException if !isValid(loc)
	 */
	default List<T> get(Location loc, Predicate<T> predicate) {
		return get(loc).stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * Remove an element from the cell at the given location.
	 *
	 * @param loc       The location of the grid cell
	 * @param predicate Predicate which should be true for elements to be removed
	 * @return Number of elements removed
	 * @throws IndexOutOfBoundsException if !isValid(loc)
	 */
	default int remove(Location loc, Predicate<T> predicate) {
		List<T> list = get(loc);
		int s = list.size();
		get(loc).removeIf(predicate);
		return s - list.size();
	}

	/**
	 * Remove an element from the cell at the given location.
	 *
	 * @param loc     The location of the grid cell
	 * @param element An element to be removed from the cell.
	 * @return Number of elements removed
	 * @throws IndexOutOfBoundsException if !isValid(loc)
	 */
	default boolean remove(Location loc, T element) {
		return get(loc).remove(element);
	}


}
