    package inf101.rogue101;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import inf101.GetStarted;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.map.GameMap;
import inf101.rogue101.objects.Carrot;
import inf101.rogue101.objects.Dust;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.IItemComparator;
import inf101.rogue101.objects.Rabbit;
import inf101.rogue101.objects.Spider;
import inf101.rogue101.objects.Wall;

public class GameMapTest {

    @BeforeEach
    void beforeEach() {
    	assertTrue(GetStarted.hasRead);
    }

    /**
	 * Tests if it is possible to add an item to an empty map.
	 */
	@Test
	void testAddThenGet() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(7, 4);
		IItem item = new Dust();
		map.add(loc, item);
		assertTrue(map.getAll(loc).contains(item));
	}
	
	/**
	 * Tests if the list of items at a given location is sorted.
	 * Sorting is in general decided by the compareTo() method.
	 * In our case we sort by the size()
	 */
	@Test
	void testSortedAdd() {
		Comparator<IItem> comp = new IItemComparator();
		GameMap gameMap = new GameMap(20, 20);
		Location location = new Location(10, 10);
		gameMap.add(location, new Rabbit());
		gameMap.add(location, new Dust());
		gameMap.add(location, new Carrot());
		List<IItem> list = gameMap.getAll(location);
		for (int i = 1; i < list.size(); i++) {
			assertTrue(comp.compare(list.get(i - 1), list.get(i)) >= 0);
		}
	}
	
	/**
	 * Checks that we can go in all 4 directions
	 */
	@Test
	void testCanGo() {
		GameMap map = new GameMap(20, 20);
		Location location = new Location(10, 10);
		Collection<Location> locations = location.allNeighbors();
		for(Location loc : locations) {
			assertTrue(map.isAvailable(loc));
			map.add(loc, new Rabbit());
			assertFalse(map.isAvailable(loc));
		}
	}

	/**
	 * Checks that we can not go into cell with another actor
	 */
	@Test
	void testCanNotGoActor() {
		GameMap map = new GameMap(20, 20);
		Location location = new Location(10, 10);
		Collection<Location> locations = location.allNeighbors();
		assertEquals(8,locations.size());
		for(Location loc : locations) {
			map.add(loc, new Rabbit());
			assertFalse(map.isAvailable(loc));
		}
	}

	/**
	 * Checks that we can not go into cell with a Wall
	 */
	@Test
	void testCanNotGoWall() {
		GameMap map = new GameMap(20, 20);
		Location location = new Location(10, 10);
		Collection<Location> locations = location.allNeighbors();
		assertEquals(8,locations.size());
		for(Location loc : locations) {
			map.add(loc, new Wall());
			assertFalse(map.isAvailable(loc));
		}
	}
	/**
	 * Checks that we can not go out of the board
	 */
	@Test
	void testCanNotGoEdge() {
		GameMap map = new GameMap(21, 21);
		for(GridDirection dir : GridDirection.values()) {
			if(dir == GridDirection.CENTER)
				continue;
			Location location = new Location(10, 10);
			for(int i=0;i<10;i++) {
				location = location.getNeighbor(dir);
			}
			
			if(map.canGo(location,dir)) {
				System.out.println(location +" " + dir);
			}
			assertFalse(map.canGo(location,dir));
		}
	}
	
	/**
	 * Adds 3 items to same position.
	 * Checks that dust is there before calling remove 
	 * and that dust is not there after calling remove
	 */
	@Test
	void testRemove() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(3,3);
		IItem item = new Dust();
		map.add(loc, new Carrot());
		map.add(loc, item);
		map.add(loc, new Rabbit());
		assertTrue(map.getAll(loc).contains(item));
		map.remove(loc, item);
		assertFalse(map.getAll(loc).contains(item));
	}

	/**
	 * Tests that returned moves are allowed moves according to canGo function
	 * and that moves not returned is not possible moves
	 */
	@Test
	void testGetPossibleMoves() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(3,3);
		map.add(loc, new Spider());
		map.add(new Location(3,4), new Wall());
		map.add(new Location(4,3), new Wall());
		map.add(new Location(2,3), new Spider());

		List<GridDirection> moves = map.getPossibleMoves(loc);
		assertFalse(moves.contains(GridDirection.CENTER),"CENTER should not be in possiblemoves");
		for(GridDirection dir : GridDirection.EIGHT_DIRECTIONS) {
			assertEquals(map.canGo(loc,dir),moves.contains(dir));
		}
	}
	
	/**
	 * Tests that getNeighbourhood returns some of the correct locations
	 */
	@Test
	void testGetNeighbourhoodDumb() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(10, 10);
		List<Location> neighbourhood = map.getNeighbourhood(loc, 3);
		assertTrue(neighbourhood.size() >= 20);
	}
	
	/**
	 * Tests that getNeighbourhood returns all the correct locations
	 */
	@Test
	void testGetNeighbourhoodCardinality() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(10, 10);
		List<Location> neighbourhood = map.getNeighbourhood(loc, 3);
		assertEquals(7*7-1, neighbourhood.size());
	}

	/**
	 * Tests that getNeighbourhood returns all the correct locations
	 * When the given location is close to the edge
	 */
	@Test
	void testGetNeighbourhoodEdgeCardinality() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(1, 2);
		List<Location> neighbourhood = map.getNeighbourhood(loc, 3);
		assertEquals(6*5-1, neighbourhood.size());
	}

	@Test
	void testGetNeighbourhoodDoesNotReturnWalls() {
		GameMap map = new GameMap(20, 20);
		map.add(new Location(10, 11), new Wall());
		Location loc = new Location(10, 10);
		List<Location> neighbourhood = map.getNeighbourhood(loc, 3);
		assertEquals((7*7-1)-1, neighbourhood.size());
	}
	
	@Test
	void testGetReachableDoesNotWalkPastWalls() {
		final int WIDTH = 20;
		GameMap map = new GameMap(WIDTH, 20);
		for (int x=0; x<WIDTH; x++) {
			map.add(new Location(x, 11), new Wall());
		}
		Location loc = new Location(10, 10);
		List<Location> neighbourhood = map.getReachable(loc, 3);
		assertEquals(7*4-1, neighbourhood.size());
		
	}
	
	@Test
	void testGetNeighbourhoodSorted() {
		GameMap map = new GameMap(20, 20);
		Location loc = new Location(10, 10);
		List<Location> neighbourhood = map.getNeighbourhood(loc, 3);
		Location current = neighbourhood.get(0);
		for (int i=1; i<neighbourhood.size(); i++) {
			Location next = neighbourhood.get(i);
			assertTrue(loc.gridDistanceTo(current) <= loc.gridDistanceTo(next));
			current = next;
		}
	}
}
