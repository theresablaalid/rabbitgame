package inf101.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/** A Grid contains a values in a 2D rectangular layout. */
public class Grid<T> implements IGrid<T> {
	private final List<T> cells;
	private final int columns;
	private final int rows;

	/**
	 * Construct a grid with the given dimensions.
	 *
	 * The initialiser function will be called with the (x,y) position of an
	 * element, and is expected to return the element to place at that position. For
	 * example:
	 *
	 * <pre>
	 * // fill all cells with the position as a string (e.g., "(2,2)")
	 * new Grid(10, 10, ((x, y) -> String.format("(%d,%d)", x, y));
	 * </pre>
	 *
	 * @param width
	 * @param height
	 * @param initialiser The initialiser function
	 */
	public Grid(int width, int height, Function<Location, T> initialiser) {
		this(width,height);
		fill(initialiser);
	}

	/**
	 * Construct a grid with the given dimensions.
	 *
	 * @param width
	 * @param height
	 * @param initElement What the cells should initially hold (possibly null)
	 */
	public Grid(int width, int height, T initElement) {
		this(width,height);
		fill(initElement);
	}

	public Grid(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.cells = new ArrayList<T>(rows*columns);
		for(int i=0; i<rows*columns; i++)
			cells.add(null);
	}

	@Override
	public int numColumns() {
		return columns;
	}

	@Override
	public int numRows() {
		return rows;
	}

	@Override
	public void set(Location loc, T elem) {
		checkLocation(loc);

		cells.set(locationToIndex(loc), elem);
	}

	/**
	 * This method checks if a given Location is within the bounds of this grid.
	 * If it is not, an IndexOutOfBoundsException is thrown.
	 * @param loc the location to check
	 */
	public void checkLocation(Location loc) {
		if(!isOnGrid(loc)) {
			throw new IndexOutOfBoundsException("Row and column indices must be within bounds");
		}
	}

	@Override
	public boolean isOnGrid(Location loc) {
		if(loc == null)
			return false;
		if(loc.row < 0 || loc.row >= rows) {
			return false;
		}

		return loc.col >= 0 && loc.col < columns;
	}

	/**
	 * This method computes which index in the list belongs to a given Location
	 */
	private int locationToIndex(Location loc) {
		return loc.row + loc.col * rows;
	}

	@Override
	public T get(Location loc) {
		checkLocation(loc);

		return cells.get(locationToIndex(loc));
	}

	@Override
	public GridLocationIterator locations() {
		return new GridLocationIterator(numRows(), numColumns());
	}

	@Override
	public IGrid<T> copy() {
		Grid<T> newGrid = new Grid<>(numRows(), numColumns());

		for(Location loc : locations())
			newGrid.set(loc,this.get(loc));
		return newGrid;
	}

	@Override
	public void fill(Function<Location, T> initialiser) {
		if (initialiser == null)
			throw new NullPointerException();

		for (Location loc : locations()) {
			set(loc, initialiser.apply(loc));
		}
	}

	@Override
	public void fill(T element) {
		for (Location loc : this.locations()) {
			set(loc,element);
		}
	}



	@Override
	public T getOrDefault(Location loc, T defaultResult) {
		T r = get(loc);
		if (r != null)
			return r;
		else
			return defaultResult;
	}


	@Override
	public Iterator<T> iterator() {
		return cells.iterator();
	}

	@Override
	public boolean canGo(Location from, GridDirection dir) {
		return isOnGrid(from.getNeighbor(dir));
	}
}
